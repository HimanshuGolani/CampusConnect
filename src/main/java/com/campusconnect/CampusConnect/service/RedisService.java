package com.campusconnect.CampusConnect.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
@Slf4j
@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public <T> T get(String key, Class<T> mappingTo) {
        try {
            String value = redisTemplate.opsForValue().get(key);
            if (value != null) {
                return new ObjectMapper().readValue(value, mappingTo);
            }
        } catch (Exception e) {
            log.error("Error while fetching from Redis for key {}: {}", key, e.getMessage(), e);
        }
        return null;
    }

    public void set(String key, Object o, Long ttl) {
        try {
            String value = new ObjectMapper().writeValueAsString(o);
            redisTemplate.opsForValue().set(key, value, ttl, TimeUnit.HOURS);
        } catch (Exception e) {
            log.error("Error while storing in Redis for key {}: {}", key, e.getMessage(), e);
        }
    }

    public void updateCache(String key, Object o, Long ttl) {
        try {
            String value = new ObjectMapper().writeValueAsString(o);
            redisTemplate.opsForValue().set(key, value, ttl, TimeUnit.HOURS);
            log.info("Cache updated for key {}", key);
        } catch (Exception e) {
            log.error("Error while updating cache for key {}: {}", key, e.getMessage(), e);
        }
    }

    // Method to remove a cache entry from Redis after DB delete
    public void removeCache(String key) {
        try {
            redisTemplate.delete(key);
            log.info("Cache removed for key {}", key);
        } catch (Exception e) {
            log.error("Error while removing cache for key {}: {}", key, e.getMessage(), e);
        }
    }

}
