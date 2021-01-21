package com.jwang261.onlineshop.order.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jwang261
 * @date 2021/1/18 5:40 PM
 */
@Configuration
public class MyFeignConfig {
    @Bean("requestInterceptor")
    public RequestInterceptor requestInterceptor(){
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                //This method is actually base on ThreadLocal, so we can also use ThreadLocal to get Info
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                //Old request
                HttpServletRequest request = attributes.getRequest();

                if(request != null){
                    //Get cookies
                    String cookie = request.getHeader("Cookie");
                    //set new request
                    template.header("Cookie",cookie);

                }else{
                    System.out.println("request is null!");
                }
            }
        };
    }
}
