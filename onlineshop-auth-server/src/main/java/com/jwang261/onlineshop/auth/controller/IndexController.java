package com.jwang261.onlineshop.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author jwang261
 * @date 2020/12/29 6:07 AM
 */
@Controller
public class IndexController {

    /**
     * 发送一个请求直接到页面
     * SpringMVC View Controller 将请求和页面映射过来
     * 详情见Config
     */


    @GetMapping("/login.html")
    public String login(){

        return "login";
    }

    @GetMapping("/reg.html")
    public String reg(){

        return "reg";
    }
}
