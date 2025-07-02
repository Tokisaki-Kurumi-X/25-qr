package com.example.unity_backend.Utils.RedisUtils.Redis;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching  // 启用缓存功能
public class RedisConfig {
    @Value("${redis.server.nodes}")
    private String redisServerNodes;

    @Value("${redis.server.password}")
    private String redisServerPassword;

//    @Bean
//    public RedisClusterConfiguration getRedisClusterConfiguration() {
//
//        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
//
//        String[] serverArray = redisServerNodes.split(",");
//        Set<RedisNode> nodes = new HashSet<RedisNode>();
//        for (String ipPort : serverArray) {
//            String[] ipAndPort = ipPort.split(":");
//            nodes.add(new RedisNode(ipAndPort[0].trim(), Integer.parseInt(ipAndPort[1])));
//        }
//        redisClusterConfiguration.setClusterNodes(nodes);
//        RedisPassword pwd = RedisPassword.of(redisServerPassword);
//        redisClusterConfiguration.setPassword(pwd);
//        return redisClusterConfiguration;
//    }
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();

        // 配置 Redis 主机和端口
        String[] serverArray = redisServerNodes.split(",");
        String ipAndPort = serverArray[0]; // 单节点模式下只有一个节点
        String[] ipAndPortParts = ipAndPort.split(":");
        String host = ipAndPortParts[0].trim();
        int port = Integer.parseInt(ipAndPortParts[1]);

        redisStandaloneConfiguration.setHostName(host); // 设置主机
        redisStandaloneConfiguration.setPort(port); // 设置端口

        // 配置密码
        RedisPassword pwd = RedisPassword.of(redisServerPassword);
        redisStandaloneConfiguration.setPassword(pwd);

        // 创建连接工厂并返回
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheWriter cacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))  // 设置缓存失效时间
                .disableCachingNullValues();  // 禁用缓存空值
        return new RedisCacheManager(cacheWriter, cacheConfig);
    }
    //指定redisTemplate类型，如下为<String, Object>
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // 创建 Jackson2JsonRedisSerializer 并配置 ObjectMapper
        Jackson2JsonRedisSerializer<Object> jacksonSeial = new Jackson2JsonRedisSerializer<>(Object.class);

        // 创建 ObjectMapper 并配置类型处理
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        // 使用新的类型处理方法配置 ObjectMapper（替代 enableDefaultTyping）
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);

        // 设置 ObjectMapper 和序列化器
        jacksonSeial.setObjectMapper(objectMapper);

        // 设置 RedisTemplate 使用 Jackson2JsonRedisSerializer
        template.setDefaultSerializer(jacksonSeial);
        return template;
    }
}