package com.consumer.config;

import com.huya.skyeye.core.redis.RedisTemplate;
import com.huya.skyeye.core.redis.util.ConfigIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Author: zhaoshuai
 * @Date: 2021/11/16
 */
@Component
public class RedisConfig {
    @Autowired
    private JobRedisConfig jobRedisConfig;
    @Value("${huya.skyeye.job.redis.master-host}")
    private String ss;

    @Autowired
    private SpringEventHelper springEventHelper;
    @Bean
    RedisTemplate jobRedisTemplate() {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setJedisPool(ConfigIO.parse(jobRedisConfig));
        return redisTemplate;
    }

    public RedisTemplate getByKey(String beanName) {
        return springEventHelper.getBean(beanName, RedisTemplate.class);
    }

}
