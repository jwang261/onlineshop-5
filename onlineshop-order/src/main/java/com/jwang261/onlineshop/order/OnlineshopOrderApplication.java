package com.jwang261.onlineshop.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author jwang261
 * @date 2020/8/20 7:15 PM
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableRabbit
@EnableFeignClients
@MapperScan("com.jwang261.onlineshop.order.dao")
//本地事务本类方法互调代理失效问题解决：
//@EnableRedisHttpSession
@EnableAspectJAutoProxy(exposeProxy = true)//动态代理，对外暴露代理对象
public class OnlineshopOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnlineshopOrderApplication.class, args);
    }
}
