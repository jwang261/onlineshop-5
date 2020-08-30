package com.jwang261.onlineshop.ware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author jwang261
 * @date 2020/8/20 7:15 PM
 */
@EnableTransactionManagement
@SpringBootApplication
@MapperScan("com.jwang261.onlineshop.ware.dao")
@EnableDiscoveryClient
@EnableFeignClients
public class OnlineshopWareApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnlineshopWareApplication.class, args);
    }
}
