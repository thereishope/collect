package com.data.server.cache.cacheFactory;

import com.data.server.cache.SimpleCache;
import com.data.share.caches.basicCache.cache.Cache;
import com.data.share.caches.basicCache.cache.CacheInit;
import com.data.share.caches.basicCache.factory.CacheFactory;

/**分布式缓存实现
 * @author jiajun_chen palading_cr@163.com
 * @title RemoteCacheFactory
 * @project collect
 * @date 2018/6/20
 */
public class RemoteCacheFactory<K,V> implements CacheFactory<K,V> {

    private Cache<K, V> cache;

    private CacheInit<K, V> cacheInit;

    public RemoteCacheFactory(CacheInit<K, V> cacheInit,Cache cache) {
        this.cache = cache;
        this.cacheInit = cacheInit;
        initCache();
    }


    public void initCache() {
        this.cacheInit.init(this.cache);
    }



    public Cache<K, V> getCache() {
        return this.cache;
    }
}
