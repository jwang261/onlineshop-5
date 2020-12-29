package com.jwang261.onlineshop.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author jwang261
 * @date 2020/12/29 6:43 AM
 */
@Configuration
public class OnlineshopWebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("login").setViewName("login");
        registry.addViewController("reg").setViewName("reg");
    }
}
