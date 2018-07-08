package com.data.gateway.proxy;

import com.data.gateway.factory.HanderManagerFactory;
import com.data.share.component.data.Bind;
import com.data.share.enums.AsyncEnums;
import com.data.share.param.BindData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 缓存请求代理
 *
 * @author jiajun_chen palading_cr@163.com
 */
public class CacheRequestProxy implements CacheRequest<BindData>{

    private static final Logger logger = LoggerFactory.getLogger(
            CacheRequestProxy.class);

    private CacheRequest cacheRequest;

    public CacheRequestProxy(CacheRequest cacheRequest) {
        this.cacheRequest = cacheRequest;
    }

    /**
     * 接收CacheDataBind子类参数
     * 发送请求并异步判断是否需要加入黑名单
     * @return
     * @methed
     * @author jiajun_chen palading_cr@163.com
     * @date 2018/6/20
     */
    public <T> T sendRequest(T t) {
        BindData bind = null;
        try {
            bind = (BindData) cacheRequest.sendRequest(t);
            tobackList(bind);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (T)bind;
    }

    /**
      *@methed
      *@return
      *@author jiajun_chen palading_cr@163.com
      *@date 2018/6/20
      *
      */
    public <T> void tobackList(T t) {
        HanderManagerFactory.
                BUFFER_STATIC_ASYNC.
                get(AsyncEnums.ASYNC_1000.getCode()).
                asyncHandle(t);
    }

    public int getCacheCount(String ipAddr) {
        return 0;
    }
}
