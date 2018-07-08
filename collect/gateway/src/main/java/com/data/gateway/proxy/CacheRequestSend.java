package com.data.gateway.proxy;

import com.data.gateway.filter.ReqPreFilter;
import com.data.gateway.filter.ReqRouteFilter;
import com.data.share.component.data.Bind;
import com.data.share.enums.RespEnums;
import com.data.share.exceptions.BuisException;
import com.data.share.param.BindData;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author jiajun_chen palading_cr@163.com
 * @title CacheRequestSend
 * @project collect
 * @date 2018/6/20-11:00
 */
public class CacheRequestSend extends AbstractCommonSender implements CacheRequest<BindData> {

    private RestTemplate restTemplate;

    private RedisTemplate redisTemplate;

    public CacheRequestSend(RestTemplate restTemplate, RedisTemplate redisTemplate) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }


    /**
     * @return
     * @methed
     * @author jiajun_chen palading_cr@163.com
     * @date 2018/6/20
     */
    public <T> T sendRequest(T t) throws Exception {
        BindData bind = (BindData)t;
        String group = bind.get(ReqRouteFilter.CACHE_GROUP,String.class);
        String code = bind.get(ReqRouteFilter.CACHE_CODE,String.class);
        String service = (String) redisTemplate.
                opsForValue().
                get(group.
                        concat("_").
                        concat(code));
        try {
            if (StringUtils.isEmpty(service)) {
                throw new BuisException(RespEnums.FAIL.getCode(),
                        RespEnums.FAIL.getMsg());
            }
            setRestTemplate(restTemplate);
            bind = post("Http://" + service + "/" + group + "/"
                    + code, bind, BindData.class);
            String key  = CACHE_LIMIT_REQUEST.
                    concat("-").concat(ReqRouteFilter.CACHE_AUTH).concat("-").
                    concat(group).concat("-").concat(code);
            //建议采用更高级版本的redis或者其他实现方式以保证原子性操作
            redisTemplate.
                    opsForValue().
                    increment(
                            key, 1);
            redisTemplate.expire(key,23,TimeUnit.HOURS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BuisException(RespEnums.FAIL.getCode(),RespEnums.FAIL.getMsg());
        }
        return (T) bind;
    }
    /**
     *@methed
     *@return
     *@author jiajun_chen palading_cr@163.com
     *@date 2018/6/20
     *
     */
   /* public DataBind sendRequest(DataBind request) {
        String service = redisTemplate.opsForValue().get(request.);
        restTemplate.postForObject("Http://" + service + "/" + cacheGroup + "/" + code, param, CacheRequest.class);
        return null;
    }*/


    /**
     * @return
     * @methed
     * @author jiajun_chen palading_cr@163.com
     * @date 2018/6/20
     */
    public int getCacheCount(String ipAddr) {
        return 0;
    }


}
