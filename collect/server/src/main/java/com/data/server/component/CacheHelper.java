package com.data.server.component;

import com.data.server.ServerApplication;
import com.data.server.cache.MemecacheRemoteCache;
import com.data.server.cache.RedisRemoteCache;
import com.data.server.cache.service.Cache0000Service0001;
import com.data.server.cache.cacheFactory.RemoteCacheFactory;
import com.data.server.cache.cacheFactory.SimpleCacheFactory;
import com.data.server.cache.service.Memcache0000Service0003;
import com.data.server.cache.service.RedisCache0000Service0002;
import com.data.share.caches.basicCache.factory.CacheFactory;

/**
 * @author jiajun_chen palading_cr@163.com
 * @title CacheHelper
 * @project collect
 * @date 2018/6/20-17:42
 */

public final class CacheHelper {

    private static RedisRemoteCache redisRemoteCache;

    private static MemecacheRemoteCache memecacheRemoteCache;

    static {
        redisRemoteCache = (RedisRemoteCache) ServerApplication.context.getBean("redisRemoteCache");
        memecacheRemoteCache = (MemecacheRemoteCache) ServerApplication.context.getBean("memecacheRemoteCache");
    }

    //缓存0000-0001
    public static final CacheFactory<String, Object> simpleCache =
            new SimpleCacheFactory<String, Object>(new Cache0000Service0001());

    //缓存0000-0002
    public static final CacheFactory<String, Object> redisCache =
            new RemoteCacheFactory(new RedisCache0000Service0002(), redisRemoteCache);

    //缓存0000-0003
    public static final CacheFactory<String, Object> memCache =
            new RemoteCacheFactory(new Memcache0000Service0003(), memecacheRemoteCache);

    public static void loadCache() {

    }


}
