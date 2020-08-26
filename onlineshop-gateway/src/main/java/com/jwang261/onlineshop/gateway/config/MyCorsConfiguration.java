package com.jwang261.onlineshop.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * @author jwang261
 * @date 2020/8/22 11:02 PM
 */

@Configuration
public class MyCorsConfiguration {

    @Bean
    public CorsWebFilter corsWebFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        //1、配置跨域
        corsConfiguration.addAllowedHeader("*");//
        corsConfiguration.addAllowedMethod("*");//请求方式
        corsConfiguration.addAllowedOrigin("*");//请求来源
        corsConfiguration.setAllowCredentials(true);//携带Cookie进行跨域
        source.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsWebFilter(source);
    }
}
