package com.jwang261.onlineshop.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
//@EnableRedisHttpSession
public class OnlineshopAuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineshopAuthServerApplication.class, args);
    }

}
