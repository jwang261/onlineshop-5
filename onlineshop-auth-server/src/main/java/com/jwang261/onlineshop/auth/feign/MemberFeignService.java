package com.jwang261.onlineshop.auth.feign;

import com.jwang261.common.utils.R;
import com.jwang261.onlineshop.auth.vo.UserLoginVo;
import com.jwang261.onlineshop.auth.vo.UserRegisterVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author jwang261
 * @date 2021/1/1 1:39 AM
 */
@FeignClient("onlineshop-member")
public interface MemberFeignService {

    @PostMapping("/member/member/register")
    R register(@RequestBody UserRegisterVo vo);

    @PostMapping("/member/member/login")
    R login(@RequestBody UserLoginVo vo);
}
