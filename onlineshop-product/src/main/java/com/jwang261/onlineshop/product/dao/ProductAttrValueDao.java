package com.jwang261.onlineshop.product.dao;

import com.jwang261.onlineshop.product.entity.ProductAttrValueEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * spu属性值
 * 
 * @author JIALIANG WANG
 * @email jwang261@syr.edu
 * @date 2020-08-20 18:14:42
 */
@Mapper
public interface ProductAttrValueDao extends BaseMapper<ProductAttrValueEntity> {
	
}
