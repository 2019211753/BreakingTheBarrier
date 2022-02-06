package com.lrm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SearchService {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 在有序集合中获取搜索key的排名
     */
    public Long rank(String zname, Object key) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.rank(zname, key);
    }

    /**
     * 向有序集合内添加元素
     */
    public void zadd(String zname, Object value, double score) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(zname, value, score);
    }

    /**
     * zscore(集合, "peter")将返回5000
     * tom: 2000
     * peter: 5000
     */
    public double zscore(String zname, Object key) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.score(zname, key);
    }

    public Set<Object> reverseRange(String zname, long start, long end) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.reverseRange(zname, start, end);
    }

}
