package com.jwang261.onlineshop.product;

import com.jwang261.onlineshop.product.dao.AttrGroupDao;
import com.jwang261.onlineshop.product.dao.SkuSaleAttrValueDao;
import com.jwang261.onlineshop.product.service.BrandService;
import com.jwang261.onlineshop.product.vo.SkuItemVo;
import com.jwang261.onlineshop.product.vo.SpuItemAttrGroupVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;


import java.util.List;
import java.util.UUID;

@SpringBootTest
class OnlineshopProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    AttrGroupDao attrGroupDao;

    @Autowired
    SkuSaleAttrValueDao skuSaleAttrValueDao;

    @Test
    public void test01(){
        System.out.println(skuSaleAttrValueDao.getSaleAttrsBySpuId(11L));
    }

    @Test
    public void test(){
        List<SpuItemAttrGroupVo> attrGroupWithAttrsBySpuId = attrGroupDao.getAttrGroupWithAttrsBySpuId(3L, 225L);
        System.out.println(attrGroupWithAttrsBySpuId);
    }

    @Test
    public void testStringRedisTemplate(){
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set("hello","world_"+ UUID.randomUUID().toString());
        String hello = ops.get("hello");
        System.out.println(hello);



    }


}
