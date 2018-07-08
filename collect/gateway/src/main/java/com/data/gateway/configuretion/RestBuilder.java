package com.data.gateway.configuretion;

import com.data.share.annotation.Retry;
import com.data.share.exceptions.BuisException;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author jiajun_chen palading_cr@163.com
 * @title RestBuilder
 * @project collect
 * @date 2018/7/8
 */
@Component
public class RestBuilder {

    @Autowired
    private RestTemplate restTemplate;


    @Retry(retry = 3,exception = BuisException.class)
    public <T> T postForObject(String url,T data,Class<T> clazz)throws Exception{
        return restTemplate.postForObject(url,data,clazz);
    }
}
