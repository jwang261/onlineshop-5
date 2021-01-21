package com.jwang261.onlineshop.order.feign;

import com.jwang261.onlineshop.order.vo.OrderItemVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author jwang261
 * @date 2021/1/17 3:25 PM
 */
@FeignClient("onlineshop-cart")
public interface CartFeignService {
    @GetMapping("/currentUserCartItems")
    List<OrderItemVo> getCurrentUserCartItems();
}
