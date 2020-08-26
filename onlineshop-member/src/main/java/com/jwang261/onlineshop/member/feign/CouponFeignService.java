package com.jwang261.onlineshop.member.feign;

import com.jwang261.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author jwang261
 * @date 2020/8/20 9:24 PM
 */
@FeignClient("onlineshop-coupon")
public interface CouponFeignService {
    @RequestMapping("/coupon/coupon/member/list")
    public R memberCoupons();

}
