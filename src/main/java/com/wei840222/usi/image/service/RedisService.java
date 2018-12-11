package com.wei840222.usi.image.service;

import com.wei840222.usi.image.model.ImageRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RedisService {
    @Autowired
	private RedisTemplate<Object, Object> redisTemplate;

    public void set(String key, ImageRecord value) {
        final ValueOperations<Object, Object> vo = this.redisTemplate.opsForValue();
        vo.set(key, value);
        log.info("set - key: {} - value: {}", key, value);
    }

    public ImageRecord get(String key) {
        final ValueOperations<Object, Object> vo = this.redisTemplate.opsForValue();
        final ImageRecord value = (ImageRecord) vo.get(key);
        log.info("get - key: {} - value: {}", key, value);
        return value;
    }
}
