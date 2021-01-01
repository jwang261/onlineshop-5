package com.jwang261.onlineshop.auth.controller;

import com.alibaba.fastjson.TypeReference;
import com.jwang261.common.utils.R;
import com.jwang261.onlineshop.auth.feign.MemberFeignService;
import com.jwang261.onlineshop.auth.vo.UserLoginVo;
import com.jwang261.onlineshop.auth.vo.UserRegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author jwang261
 * @date 2020/12/31 9:51 AM
 */

@Controller
public class LoginController {

    @Autowired
    MemberFeignService memberFeignService;

    //TODO 重定向携带数据利用session原理，但是分布式不支持
    @PostMapping("/register")
    public String register(@Valid UserRegisterVo vo, BindingResult result,
                           RedirectAttributes redirectAttributes
                           ) {

        if (result.hasErrors()) {

            /**
            result.getFieldErrors().stream().map(fieldError -> {
                String field = fieldError.getField();
                String defaultMessage = fieldError.getDefaultMessage();
                errors.put(field,defaultMessage);
                return errors;
            });
             **/
            Map<String, String> errors = result.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, fieldError -> {
                return fieldError.getDefaultMessage();
            }));

            redirectAttributes.addFlashAttribute("errors",errors);
            //此处可能出的一个问题：因为这方法是由post请求的，如果用forward，则是原封不动的转发post请求
            //而我们config中设定的路径映射是get请求，所以页面会报405错误
            //return "forward:reg";

            return "redirect:http://auth.jwang261-shop.com/reg.html";
        }

        //真正的注册，调用远程服务
        //1.校验验证码，如果成功则删除 TODO from redis and delete
        String code = vo.getCode();
        //2.校验成功调用远程服务
        R r = memberFeignService.register(vo);
        if(r.getCode() == 0){
            //注册成功回到首页，回到登陆页
            return "redirect:http://auth.jwang261-shop.com/login.html";
        }else{
            HashMap<String, String> errors = new HashMap<>();
            errors.put("msg",r.getData("msg",new TypeReference<String>(){}));
            redirectAttributes.addFlashAttribute("errors",errors);

            return "redirect:http://auth.jwang261-shop.com/reg.html";
        }


    }
    @PostMapping("/login")
    public String login(UserLoginVo vo, RedirectAttributes redirectAttributes){
        R login = memberFeignService.login(vo);
        if(login.getCode()==0){

            return "redirect:http://jwang261-shop.com";
        }else{
            Map<String,String> errors = new HashMap<>();
            errors.put("msg",login.getData("msg",new TypeReference<String>(){}));
            redirectAttributes.addFlashAttribute("errors",errors);
            return "redirect:http://auth.jwang261-shop.com/login.html";
        }
    }

}
