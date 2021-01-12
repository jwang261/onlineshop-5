package com.jwang261.onlineshop.cart.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author jwang261
 * @date 2020/12/28 3:20 AM
 */
@ConfigurationProperties(prefix = "onlineshop5.thread")
@Component
@Data
public class ThreadPoolConfigProperties {
    private Integer coreSize;
    private Integer maxSize;
    private Integer keepAliveTime;
}
