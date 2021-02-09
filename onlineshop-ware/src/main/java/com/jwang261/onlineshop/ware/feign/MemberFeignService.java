package com.jwang261.onlineshop.ware.feign;

import com.jwang261.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author jwang261
 * @date 2021/2/9 11:42 AM
 */
@FeignClient("onlineshop-member")
public interface MemberFeignService {
    @RequestMapping("/member/memberreceiveaddress/info/{id}")
    R addrInfo(@PathVariable("id") Long id);
}
