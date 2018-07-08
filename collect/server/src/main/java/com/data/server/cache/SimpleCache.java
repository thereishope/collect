package com.data.server.cache;
import com.data.share.caches.basicCache.cache.Cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**基于内存实现的cache
 * 适用于单节点
 * @author jiajun_chen palading_cr@163.com
 * @title SimpleCache
 * @project share
 * @date 2018/6/21
 */
public class SimpleCache<K, V> implements Cache<K, V> {

	private Map<K, V> CACHES = new ConcurrentHashMap<K, V>();

	public void put(K k, V v) {
		CACHES.put(k, v);
	}

	public V get(K k) {
		return CACHES.get(k);
	}

	public void clear() {
		CACHES.clear();
	}

}
