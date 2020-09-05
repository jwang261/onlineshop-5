package com.jwang261.onlineshop.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jwang261.onlineshop.product.service.CategoryBrandRelationService;
import com.jwang261.onlineshop.product.vo.Catalog2Vo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwang261.common.utils.PageUtils;
import com.jwang261.common.utils.Query;

import com.jwang261.onlineshop.product.dao.CategoryDao;
import com.jwang261.onlineshop.product.entity.CategoryEntity;
import com.jwang261.onlineshop.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        //1 search all
        List<CategoryEntity> entities = baseMapper.selectList(null);

        //stream -> filter by
        //functional interface (Predicate -> level == 1)
        //collect by Collectors
        List<CategoryEntity> level1Menu = entities.stream().filter((categoryEntity) -> {
            return categoryEntity.getCatLevel() == 1;
        }).map((menu) -> {
            menu.setChildren(getChildren(menu, entities));
            return menu;
        }).sorted((menu1, menu2) -> {
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());

        return level1Menu;
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        //TODO 检查当前菜单是否被其他的地方引用

        //逻辑删除

        baseMapper.deleteBatchIds(asList);
    }

    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();

        findParentPath(catelogId, paths);

        return paths.toArray(new Long[paths.size()]);
    }

    @Override
    @Transactional
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
    }

    @Override
    public List<CategoryEntity> getLevel1Categories() {
        List<CategoryEntity> categoryEntities = this.baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0L));
        return categoryEntities;
    }

    @Override
    public Map<String, List<Catalog2Vo>> getCatalogJson() {
        //Redis Logic

        //1、 set null value

        //2、 random expire time

        //3、 lock
        Map<String, List<Catalog2Vo>> result;
        String catalogJSON = redisTemplate.opsForValue().get("catalogJSON");
        if (StringUtils.isEmpty(catalogJSON)) {
            System.out.println("cache not hit");
            Map<String, List<Catalog2Vo>> catalogJsonFromDB = getCatalogJsonFromDBWithRedisLock();
            result = catalogJsonFromDB;
        } else {
            System.out.println("hit, return cache data");
            result = JSON.parseObject(catalogJSON, new TypeReference<Map<String, List<Catalog2Vo>>>() {
            });
        }

        return result;
    }

    public Map<String, List<Catalog2Vo>> getCatalogJsonFromDBWithRedisLock()  {

        //
        String uuid = UUID.randomUUID().toString();
        //atomic lock
        Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", uuid,10,TimeUnit.SECONDS);
        if(lock){
            System.out.println("get distributed lock succeed");
            Map<String, List<Catalog2Vo>> dataFromDB;
            try {
                dataFromDB = getDataFromDB();
            } finally {

                String script = "if redis.call(\"get\",KEYS[1]) == ARGV[1]\n" +
                        "then\n" +
                        "    return redis.call(\"del\",KEYS[1])\n" +
                        "else\n" +
                        "    return 0\n" +
                        "end";
                //atomic unlock
                Long lock1 = redisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class)
                        , Arrays.asList("lock"), uuid);
            }
            //redisTemplate.delete("lock");
//            String lockValue = redisTemplate.opsForValue().get("lock");
//            if(uuid.equals(lockValue)){
//                redisTemplate.delete("lock");
//            }
            // Use Lua Script
            return dataFromDB;
        }else{
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("get distributed lock x succeed, retry");
            //failed retry

            return getCatalogJsonFromDBWithRedisLock();
        }


    }

    private Map<String, List<Catalog2Vo>> getDataFromDB() {
        String catalogJSON = redisTemplate.opsForValue().get("catalogJSON");
        if (!StringUtils.isEmpty(catalogJSON)) {

            return JSON.parseObject(catalogJSON, new TypeReference<Map<String, List<Catalog2Vo>>>() {
            });
        }
        System.out.println("query database");
        List<CategoryEntity> selectList = baseMapper.selectList(null);

        List<CategoryEntity> level1Categories = getParent_cid(selectList, 0L);
        Map<String, List<Catalog2Vo>> map = level1Categories.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
            //1、每一个的1级分类，查到这个一级分类的子分类
            List<CategoryEntity> categoryEntities = getParent_cid(selectList, v.getCatId());

            //2、封装上面的结果
            List<Catalog2Vo> catalog2Vos = null;
            if (categoryEntities != null) {
                catalog2Vos = categoryEntities.stream().map(l2 -> {
                    Catalog2Vo catalog2Vo = new Catalog2Vo(v.getCatId().toString(), null, l2.getCatId().toString(), l2.getName());
                    //1、找当前二级分类的三级分类封装成vo
                    List<CategoryEntity> level3Catalog = getParent_cid(selectList, l2.getCatId());
                    if (level3Catalog != null) {
                        List<Catalog2Vo.Catalog3Vo> l3List = level3Catalog.stream().map(l3 -> {
                            //封装成指定格式
                            Catalog2Vo.Catalog3Vo catalog3Vo = new Catalog2Vo.Catalog3Vo(l2.getCatId().toString(), l3.getCatId().toString(), l3.getName());
                            return catalog3Vo;
                        }).collect(Collectors.toList());
                        catalog2Vo.setCatalog3List(l3List);

                    }


                    return catalog2Vo;
                }).collect(Collectors.toList());
            }

            return catalog2Vos;
        }));

        String s = JSON.toJSONString(map);
        redisTemplate.opsForValue().set("catalogJSON", s, 1, TimeUnit.DAYS);
        return map;
    }


    public Map<String, List<Catalog2Vo>> getCatalogJsonFromDBWithLocalLock() {
        /**
         * Optimization:
         * n queries -> 1 total query
         */

        //TODO lock local thread, cannot lock all the request in distributed system
        synchronized (this) {
            // check redis

            return getDataFromDB();
        }

    }

    private List<CategoryEntity> getParent_cid(List<CategoryEntity> selectList, Long parent_cid) {
        //return baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", catId));
        List<CategoryEntity> collect = selectList.stream().filter(item -> item.getParentCid() == parent_cid).collect(Collectors.toList());
        return collect;

    }

    private List<Long> findParentPath(Long catelogId, List<Long> paths) {
        paths.add(0, catelogId);
        CategoryEntity byId = this.getById(catelogId);
        if (byId.getParentCid() != 0) {

            findParentPath(byId.getParentCid(), paths);
        }
        return paths;
    }

    private List<CategoryEntity> getChildren(CategoryEntity root, List<CategoryEntity> all) {

        List<CategoryEntity> children = all.stream().filter(categoryEntity -> {
            return categoryEntity.getParentCid() == root.getCatId();
        }).map((categoryEntity) -> {
            // find children of current child
            categoryEntity.setChildren(getChildren(categoryEntity, all));
            return categoryEntity;
            // sort the children of roots
        }).sorted((menu1, menu2) -> {
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());

        return children;
    }

}