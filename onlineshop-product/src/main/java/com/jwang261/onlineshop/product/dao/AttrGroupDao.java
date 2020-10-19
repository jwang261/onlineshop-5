package com.jwang261.onlineshop.product.dao;

import com.jwang261.onlineshop.product.entity.AttrGroupEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jwang261.onlineshop.product.vo.SkuItemVo;
import com.jwang261.onlineshop.product.vo.SpuItemAttrGroupVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性分组
 * 
 * @author JIALIANG WANG
 * @email jwang261@syr.edu
 * @date 2020-08-20 18:14:42
 */
@Mapper
public interface AttrGroupDao extends BaseMapper<AttrGroupEntity> {

    List<SpuItemAttrGroupVo> getAttrGroupWithAttrsBySpuId(@Param("spuId") Long spuId, @Param("catalogId") Long catalogId);
}
