package com.data.server.cache;

import com.data.share.caches.basicCache.cache.Cache;
import org.springframework.stereotype.Component;

/**MemecacheRemote缓存
 * @author jiajun_chen palading_cr@163.com
 * @title MemecacheRemoteCache
 * @project collect
 * @date 2018/6/21
 */
@Component
public class MemecacheRemoteCache<K, V> implements Cache<K, V> {



    public void put(K k, V v) {

    }

    public V get(K k) {
        return null;
    }

    public void clear() {

    }
}
