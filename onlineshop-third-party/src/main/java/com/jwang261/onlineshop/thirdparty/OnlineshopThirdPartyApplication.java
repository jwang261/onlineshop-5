package com.jwang261.onlineshop.thirdparty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OnlineshopThirdPartyApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineshopThirdPartyApplication.class, args);
    }

}
