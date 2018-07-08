package com.data.gateway.proxy;

import com.data.share.component.data.Bind;

/**
 * 缓存请求接口
 *
 * @author jiajun_chen palading_cr@163.com
 */
public interface CacheRequest<T> {

    public static String CACHE_LIMIT_REQUEST = "reqlimit";

    //设置不同的CacheRequest对象
    public  <T> T sendRequest(T request)throws Exception;

    //统计某一时段内，某个ip地址的请求次数，并加以限制
    public int getCacheCount(String ipAddr);


}
