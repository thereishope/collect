package com.data.server.cache;

import com.data.share.caches.basicCache.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**redis缓存实现
 * 简单实现为主，生产上必须做好高可用等
 * @author jiajun_chen palading_cr@163.com
 * @title RedisRemoteCache
 * @project collect
 * @date 2018/6/21
 */
@Component
public class RedisRemoteCache<K,V> implements Cache<K,V> {

    @Autowired
    private RedisTemplate redisTemplate;

    public void put(K k, V v) {
        if(!redisTemplate.hasKey(k)){
            redisTemplate.opsForValue().set(k,v);
        }
    }

    public V get(K k) {
        return (V) redisTemplate.opsForValue().get(k);
    }

    public void clear() {

    }
}
