package com.data.share.caches.basicCache.factory;


import com.data.share.caches.basicCache.cache.Cache;

public interface CacheFactory<K,V> {
	
	public void initCache();
		
	public Cache<K, V> getCache();
}
