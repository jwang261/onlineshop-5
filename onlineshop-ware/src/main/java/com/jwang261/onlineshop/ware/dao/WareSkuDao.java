package com.jwang261.onlineshop.ware.dao;

import com.jwang261.onlineshop.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品库存
 * 
 * @author JIALIANG WANG
 * @email jwang261@syr.edu
 * @date 2020-08-20 18:55:05
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {
	
}
