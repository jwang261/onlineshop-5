package com.jwang261.onlineshop.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author jwang261
 * @date 2020/8/20 7:15 PM
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.jwang261.onlineshop.member.feign")
@MapperScan("com.jwang261.onlineshop.member.dao")
public class OnlineshopMemberApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnlineshopMemberApplication.class, args);
    }
}
