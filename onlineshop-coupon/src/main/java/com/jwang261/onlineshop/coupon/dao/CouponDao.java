package com.jwang261.onlineshop.coupon.dao;

import com.jwang261.onlineshop.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author JIALIANG WANG
 * @email jwang261@syr.edu
 * @date 2020-08-20 18:45:11
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
