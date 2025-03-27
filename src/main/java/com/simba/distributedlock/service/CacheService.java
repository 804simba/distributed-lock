package com.simba.distributedlock.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CacheService {
    private final Logger log = LoggerFactory.getLogger(CacheService.class);

    private final RedisTemplate<String, Object> redisTemplate;

    public CacheService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveToCache(String key, String value) {
        redisTemplate.opsForValue().set(key, value, 60, TimeUnit.SECONDS);
    }

    public String getFromCache(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }
}
