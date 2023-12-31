package com.example.binanceAnalyzer.Services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class RedisOperations {
    RedisTemplate<String, Object> redisTemplate;
    public Object getLastFromInMemoryDBByKey(String key){
       int size=redisTemplate.opsForHash().size(key).intValue();
        return  redisTemplate
                .opsForHash()
                .values(key)
                .get(size-1);
    }
    public List<Object> getAllFromInMemoryDbByKey(String key){
        return redisTemplate.opsForHash().values(key);
    }

}
