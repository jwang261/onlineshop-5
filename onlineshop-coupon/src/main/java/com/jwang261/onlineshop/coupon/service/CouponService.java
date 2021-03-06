package com.jwang261.onlineshop.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jwang261.common.utils.PageUtils;
import com.jwang261.onlineshop.coupon.entity.CouponEntity;

import java.util.Map;

/**
 * 优惠券信息
 *
 * @author JIALIANG WANG
 * @email jwang261@syr.edu
 * @date 2020-08-20 18:45:11
 */
public interface CouponService extends IService<CouponEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

