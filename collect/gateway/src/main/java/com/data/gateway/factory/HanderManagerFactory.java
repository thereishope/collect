package com.data.gateway.factory;

import com.data.share.component.async.AsyncHandler;
import com.data.share.component.secure.SecureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * handler处理器工厂
 *
 * @author jiajun_chen palading_cr@163.com
 */
@Configuration
public class HanderManagerFactory {

    //需要对安全处理器进行排序，便于迭代处理安全验证
    public static final Map<String, SecureHandler> BUFFER_STATIC_SECURE =
            new TreeMap<String, SecureHandler>(new Comparator<String>() {
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            });

    //异步处理器
    public static final Map<String, AsyncHandler> BUFFER_STATIC_ASYNC =
            new ConcurrentHashMap<String, AsyncHandler>();


    /**
     * 加载安全处理器对象到缓存中
     *
     * @return
     * @methed
     * @author jiajun_chen palading_cr@163.com
     * @date 2018/6/20
     */


    @Autowired
    public void setSecureHandlers(Map<String, SecureHandler> handers) {
        for (Map.Entry<String, SecureHandler> item : handers.entrySet()) {
            BUFFER_STATIC_SECURE.put(item.getValue().getCode(), item.getValue());
        }
    }

    /**
     * 加载异步处理器到缓存中
     *
     * @return
     * @methed
     * @author jiajun_chen palading_cr@163.com
     * @date 2018/6/20
     */
    @Autowired
    public void setAsyncHandlers(Map<String, AsyncHandler> handlers) {
        for (Map.Entry<String, AsyncHandler> item : handlers.entrySet()) {
            BUFFER_STATIC_ASYNC.put(item.getValue().getCode(), item.getValue());
        }
    }
}


