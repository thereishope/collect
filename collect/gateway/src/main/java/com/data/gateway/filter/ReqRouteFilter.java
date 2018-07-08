package com.data.gateway.filter;

import com.data.gateway.proxy.CacheRequest;
import com.data.gateway.proxy.CacheRequestProxy;
import com.data.gateway.proxy.CacheRequestSend;
import com.data.share.component.data.Bind;
import com.data.share.component.data.CacheDataBind;
import com.data.share.enums.RespEnums;
import com.data.share.exceptions.BuisException;
import com.data.share.param.BindData;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * route过滤器
 *
 * @author jiajun_chen palading_cr@163.com
 */
@Component
public class ReqRouteFilter extends ZuulFilter {

    public static final String CACHE_GROUP = "group";
    public static final String CACHE_CODE = "code";
    public static final String CACHE_IPADDR = "ipAddr";
    public static final String CACHE_AUTH = "auth";


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return 200 == ctx.getResponseStatusCode();
    }

    /**
     * 根据参数匹配eureka上的服务
     * 利用restTemplate请求服务端数据
     * @return
     * @methed
     * @author jiajun_chen palading_cr@163.com
     * @date 2018/6/20
     */
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        ReqPreFilter.SecureVO secure = (ReqPreFilter.SecureVO) ctx.get("secure");
        try {
            String cacheGroup = secure.getGroup();
            String code = secure.getCode();
            //根据group和code获取服务
            String service = (String) redisTemplate.opsForValue().get(cacheGroup + "_" + code);
            if (StringUtils.isNotEmpty(service)) {
                //通过代理完成请求发送等逻辑
                CacheRequest cacheRequest = new CacheRequestSend(restTemplate, redisTemplate);
                CacheRequestProxy cacheRequestProxy = new CacheRequestProxy(cacheRequest);
                BindData data = getBindData(secure);
                data = (BindData) cacheRequestProxy.sendRequest(data);
                String result = data.get("result", String.class);
                if (StringUtils.isNotEmpty(result)) {
                    ctx.setResponseStatusCode(200);
                    ctx.setSendZuulResponse(true);
                    ctx.setResponseBody(result);
                } else {
                    ctx.setResponseStatusCode(400);
                    ctx.setSendZuulResponse(false);
                    ctx.setResponseBody(null);
                }

            }

        } catch (Exception e) {
            ctx.setResponseStatusCode(400);
            ctx.setSendZuulResponse(false);
            ctx.setResponseBody(null);
        }
        return null;
    }

    /**
     * @author jiajun_chen palading_cr@163.com
     * @date 2018/6/29
     */
    public BindData getBindData(ReqPreFilter.SecureVO secure) {
        BindData data = new BindData();
        data.set(CACHE_IPADDR, secure.getIpaddr());
        data.set(CACHE_AUTH, secure.getAuth());
        data.set(CACHE_GROUP, secure.getGroup());
        data.set(CACHE_CODE, secure.getCode());
        return data;
    }

}
