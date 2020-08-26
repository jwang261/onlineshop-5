package com.jwang261.onlineshop.product.dao;

import com.jwang261.onlineshop.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author JIALIANG WANG
 * @email jwang261@syr.edu
 * @date 2020-08-20 18:14:42
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
