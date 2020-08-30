package com.jwang261.onlineshop.product.feign;

import com.jwang261.common.to.SkuReductionTo;
import com.jwang261.common.to.SpuBoundsTo;
import com.jwang261.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author jwang261
 * @date 2020/8/29 12:11 PM
 */
@FeignClient("onlineshop-coupon")
public interface CouponFeignService {

    @PostMapping("/coupon/spubounds/save")
    R saveSpuBounds(@RequestBody SpuBoundsTo spuBoundsTo);

    @PostMapping("/coupon/skufullreduction/saveinfo")
    R saveSkuReduction(@RequestBody SkuReductionTo skuReductionTo);
}
