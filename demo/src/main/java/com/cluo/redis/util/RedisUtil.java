package com.cluo.redis.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {


    @Autowired
    private RedisTemplate redisTemplate;

    public void setExpire(String key, long time) {
        redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    public long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public void set(String key, Object vaule) {
        redisTemplate.opsForValue().set(key, vaule);
    }

    public void set(String key, Object vaule, long time) {
        redisTemplate.opsForValue().set(key, vaule, time, TimeUnit.SECONDS);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void hmSet(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    public Map<String, Object> hmGet(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public Object getObject(String key) {
        return redisTemplate.boundValueOps(key).get();
    }

    public long incr(String key, long increment) {
        return redisTemplate.opsForValue().increment(key, increment);
    }
}
