package com.jwang261.onlineshop.product.feign;

import com.jwang261.common.to.SkuHasStockVo;
import com.jwang261.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author jwang261
 * @date 2020/8/31 8:30 PM
 */
@FeignClient("onlineshop-ware")
public interface WareFeignService {

    @PostMapping("/ware/waresku/hasstock")
    R getSkusHasStock(@RequestBody List<Long> skuIds);

}
