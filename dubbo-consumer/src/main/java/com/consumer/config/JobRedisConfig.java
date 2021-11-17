package com.consumer.config;

import com.huya.skyeye.core.redis.AbstractJedisConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zhaoshuai
 * @Date: 2021/11/16
 */
@ConfigurationProperties(
        prefix = "huya.skyeye.job.redis"
)
@Configuration
public class JobRedisConfig extends AbstractJedisConfig {
}
