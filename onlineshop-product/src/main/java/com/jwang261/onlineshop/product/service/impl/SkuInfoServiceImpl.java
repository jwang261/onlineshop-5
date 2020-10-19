package com.jwang261.onlineshop.product.service.impl;

import com.jwang261.onlineshop.product.entity.SkuImagesEntity;
import com.jwang261.onlineshop.product.entity.SpuInfoDescEntity;
import com.jwang261.onlineshop.product.service.AttrGroupService;
import com.jwang261.onlineshop.product.service.SkuImagesService;
import com.jwang261.onlineshop.product.service.SpuInfoDescService;
import com.jwang261.onlineshop.product.vo.SkuItemVo;
import com.jwang261.onlineshop.product.vo.SpuItemAttrGroupVo;
import com.sun.org.apache.bcel.internal.generic.UnconditionalBranch;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwang261.common.utils.PageUtils;
import com.jwang261.common.utils.Query;

import com.jwang261.onlineshop.product.dao.SkuInfoDao;
import com.jwang261.onlineshop.product.entity.SkuInfoEntity;
import com.jwang261.onlineshop.product.service.SkuInfoService;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    //TODO c.205
    @Autowired
    SkuImagesService imagesService;
    @Autowired
    SpuInfoDescService spuInfoDescService;
    @Autowired
    AttrGroupService attrGroupService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<SkuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSkuInfo(SkuInfoEntity skuInfoEntity) {
        this.baseMapper.insert(skuInfoEntity);
    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        QueryWrapper<SkuInfoEntity> wrapper = new QueryWrapper<>();

        String key = (String) params.get("key");
        if(!StringUtils.isEmpty(key)){
            wrapper.and(w->{
                w.eq("squ_id", key).or().like("squ_name", key);
            });
        }
        String catelogId = (String) params.get("catelogId");
        if(!StringUtils.isEmpty(catelogId) && !"0".equalsIgnoreCase(catelogId)){

            wrapper.eq("catalog_id", catelogId);
        }
        String brandId = (String) params.get("brandId");
        if(!StringUtils.isEmpty(brandId) && !"0".equalsIgnoreCase(brandId)){
            wrapper.eq("brand_id", brandId);
        }
        String min = (String) params.get("min");
        if(!StringUtils.isEmpty(min)){
            wrapper.ge("price", min);
        }
        String max = (String) params.get("max");

        if(!StringUtils.isEmpty(max)){

            try {
                BigDecimal bigDecimal = new BigDecimal(max);
                if (bigDecimal.compareTo(new BigDecimal("0")) == 1){
                    wrapper.le("price", bigDecimal);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                wrapper
        );
        return new PageUtils(page);
    }

    @Override
    public List<SkuInfoEntity> getSkusBySpuId(Long spuId) {
        List<SkuInfoEntity> skuInfoEntities = this.list(new QueryWrapper<SkuInfoEntity>().eq("spu_id", spuId));


        return skuInfoEntities;
    }


    //TODO c.205 needs override from interface
    //@Override
    public SkuItemVo item(Long skuId){
        SkuItemVo skuItemVo = new SkuItemVo();
        //1. 基本信息获取 pms_sku_info
        SkuInfoEntity info = getById(skuId);
        Long catalogId = info.getCatalogId();
        Long spuId = info.getSpuId();
        skuItemVo.setInfo(info);


        //2. sku图片信息 pms_sku_images
        List<SkuImagesEntity> images =  imagesService.getImagesBySkuId(skuId);
        skuItemVo.setImages(images);
        //3. 获取spu的销售属性组合

        //4. 获取spu的介绍

        SpuInfoDescEntity spuInfoDescEntity = spuInfoDescService.getById(spuId);
        skuItemVo.setDesp(spuInfoDescEntity);
//        spuInfoDescEntity.getDecript()
        //5. 获取spu的规格参数信息
        List<SpuItemAttrGroupVo> attrGroupVos = attrGroupService.getAttrGroupWithAttrsBySpuId(spuId, catalogId);
        skuItemVo.setGroupAttrs(attrGroupVos);
        return null;
    }
}