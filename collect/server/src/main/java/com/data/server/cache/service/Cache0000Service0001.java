package com.data.server.cache.service;

import com.data.share.caches.basicCache.cache.Cache;
import com.data.share.caches.basicCache.cache.CacheInit;
import com.data.share.utils.JsonUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiajun_chen palading_cr@163.com
 * @title Cache0000Service0001
 * @project collect
 * @date 2018/6/21
 */
public class Cache0000Service0001 implements CacheInit {


    /**简单缓存测试
      *@methed
      *@return
      *@author jiajun_chen palading_cr@163.com
      *@date 2018/6/21
      *
      */
    public void init(Cache cache) {
        Map<String,Integer> map = new HashMap<String, Integer>();
        for(int i=0;i<100;i++){
            map.put(String.valueOf(i),i);
        }
        cache.put("cache-0000-0001",JsonUtils.toJson(map));
    }
}
