package com.jwang261.onlineshop.coupon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author jwang261
 * @date 2020/8/20 7:15 PM
 */
@SpringBootApplication
@MapperScan("com.jwang261.onlineshop.coupon.dao")
@EnableDiscoveryClient
public class OnlineshopCouponApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnlineshopCouponApplication.class, args);
    }
}
