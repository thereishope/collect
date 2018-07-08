package com.data.share.caches.basicCache.cache;

public interface Cache<K, V> {
	
	public void put(K k, V v);

	public V get(K k);

	public void clear();
}
