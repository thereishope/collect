package com.data.share.caches.basicCache.cache;

public interface CacheInit<K,V> {
	
	public void init(final Cache<K, V> cache);
}
