package com.data.gateway.proxy;

import com.data.gateway.configuretion.RestBuilder;
import com.data.share.annotation.Retry;
import com.data.share.exceptions.BuisException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;
import java.net.SocketException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**通用请求基类
 * @author jiajun_chen palading_cr@163.com
 * @title AbstractCommonSender
 * @project collect
 * @date 2018/7/8
 */
public abstract  class AbstractCommonSender {


    private RestTemplate restTemplate;

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public static Map<String,Method> CACHE_METHOD = new ConcurrentHashMap<String,Method>();

    private static String PRIFX_NAME = "sendBy";


    /**发送请求并重试
      *@author jiajun_chen palading_cr@163.com
      *@date 2018/7/8
      *
      */
    public  <T> T post(String url,T data,Class<T> clazz){
        T t = null;
        try {
             t = sendByPost(url,data,clazz);
        } catch (Exception e) {
            if(e instanceof SocketException){
                boolean response = false;
                String name = Thread.currentThread().getStackTrace()[1].getMethodName();
                Method method = CACHE_METHOD.get(name);
                int rerty = method.getAnnotation(Retry.class).retry();
                for(int i= 0;i<rerty;i++){
                    try {
                        t = sendByPost(url,data,clazz);
                        if(!(t instanceof Exception)){
                            response = true;
                            return t;
                        }
                    } catch (Exception e1) {
                        if(e instanceof SocketException){
                            continue;
                        }
                    }
                }
                if(!response){
                    return null;
                }
            }
        }
        return t;
    }

    /**post请求
      *@author jiajun_chen palading_cr@163.com
      *@date 2018/7/8
      *
      */
    @Retry(retry = 3)
    public <T> T sendByPost(String url,T data,Class<T> clazz)throws Exception{
        return restTemplate.postForObject(url,data,clazz);
    }

    /**get请求
      *@author jiajun_chen palading_cr@163.com
      *@date 2018/7/8
      *
      */
    @Retry(retry = 4)
    public <T> T sendByGet(String url,Class<T> clazz)throws Exception{
        return restTemplate.getForObject(url,clazz);
    }

    /**将方法加入到缓存中
      *@author jiajun_chen palading_cr@163.com
      *@date 2018/7/8
      *
      */
    private Method get(String methodName){
        Method [] method =  this.getClass().getMethods();
        if(!CACHE_METHOD.containsKey(methodName)){
            for (Method met : method){
                if(met.getName().startsWith(PRIFX_NAME)){
                    CACHE_METHOD.put(met.getName(),met);
                }
            }
        }
        return CACHE_METHOD.get(methodName);

    }
}
