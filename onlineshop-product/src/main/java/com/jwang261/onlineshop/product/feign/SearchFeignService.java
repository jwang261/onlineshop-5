package com.jwang261.onlineshop.product.feign;

import com.jwang261.common.to.es.SkuEsModel;
import com.jwang261.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author jwang261
 * @date 2020/8/31 10:10 PM
 */
@FeignClient("onlineshop-search")
public interface SearchFeignService {
    @PostMapping("/search/save/product")
    public R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels);
}
