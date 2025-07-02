package com.example.unity_backend.Utils.RedisUtils.RedisUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

////存储
//redisTemplate.opsForValue().set(keyName, valueObject, Duration.ofXXX(n));
////Duration.ofNanos() 纳秒
////Duration.ofMillis() 毫秒
////Duration.ofSeconds() 秒
////Duration.ofMinutes() 分钟
////Duration.ofHours() 小时
////Duration.ofDays() 天
//
////查询
//redisTemplate.opsForValue().get(keyName);
@Component
public class RedisUtil {
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisUtil(RedisTemplate<String, Object> redistemplate){
        this.redisTemplate=redistemplate;
    }

    // 设置值
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(2));
    }

    // 获取值
    public Object get(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            // 如果没有找到键，返回 null
            return null;
        }
        return value;
    }

    // 删除值
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    // 设置值并指定过期时间
    public void setWithExpire(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    // 操作哈希结构
    public void putHash(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public Object getHash(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }


}
