package com.gjq.planet.blog.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * @author: gjq0117
 * @date: 2024/5/15 08:56
 * @description:
 */
@Configuration
public class RedisConfig {

    private final RedisConnectionFactory redisConnectionFactory;

    @Autowired
    public RedisConfig(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(redisKeyExpirationListener(), new PatternTopic("__keyevent@*__:expired"));
        return container;
    }

    @Bean
    public RedisMessageListener redisKeyExpirationListener() {
        return new RedisMessageListener();
    }

}
