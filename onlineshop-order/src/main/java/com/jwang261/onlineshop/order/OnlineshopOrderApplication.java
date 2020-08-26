package com.jwang261.onlineshop.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author jwang261
 * @date 2020/8/20 7:15 PM
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.jwang261.onlineshop.order.dao")
public class OnlineshopOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnlineshopOrderApplication.class, args);
    }
}
