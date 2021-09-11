package com.zxz.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    /*
    * Redis的序列化
    * */
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        //        String类型的key序列器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //        String类型的value序列器
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        //        Hash类型的key序列器
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //        Hash类型的value序列器
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }


}
