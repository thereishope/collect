package com.data.server.cache.cacheFactory;


import com.data.server.cache.SimpleCache;
import com.data.share.caches.basicCache.cache.Cache;
import com.data.share.caches.basicCache.cache.CacheInit;
import com.data.share.caches.basicCache.factory.CacheFactory;

/**简单缓存工厂适用于单机节点
 * @author jiajun_chen palading_cr@163.com
 * @title SimpleCacheFactory
 * @project collect
 * @date 2018/6/20-11:34
 */
public class SimpleCacheFactory<K, V> implements CacheFactory<K, V> {

	private Cache<K, V> cache;
	
	private CacheInit<K, V> cacheInit;

	public SimpleCacheFactory(CacheInit<K, V> cacheInit) {
		this.cache = new SimpleCache<K, V>();
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
