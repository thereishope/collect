package com.data.server.cache.service;

import com.data.share.caches.basicCache.cache.Cache;
import com.data.share.caches.basicCache.cache.CacheInit;
import com.data.share.utils.JsonUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * redis缓存加载实现
 * @author jiajun_chen palading_cr@163.com
 * @title RedisCache0000Service0002
 * @project collect
 * @date 2018/6/21
 */
public class RedisCache0000Service0002 implements CacheInit {

    public void init(Cache cache) {
        Map<String,Object>  map = new HashMap<String, Object>();

        for(int i = 0;i<100;i++){
            map.put(String.valueOf(i),String.valueOf(i));
        }

        cache.put("cache-0000-0002",JsonUtils.toJson(map));
    }
}
