package com.jwang261.onlineshop.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author jwang261
 * @date 2020/8/20 6:22 PM
 */
@MapperScan("com.jwang261.onlineshop.product.dao")
@SpringBootApplication
@EnableDiscoveryClient
public class OnlineshopProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnlineshopProductApplication.class, args);
    }
}
