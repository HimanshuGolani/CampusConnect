package com.campusconnect.CampusConnect.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void testMethod(){
        redisTemplate.opsForValue().set("email","himanshu@gmail.com");
        Object email = redisTemplate.opsForValue().get("email");
        assert Objects.equals(email, "himanshu@gmail.com");
    }

}
