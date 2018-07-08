package com.data.gateway.filter;

import com.data.gateway.factory.HanderManagerFactory;
import com.data.share.component.secure.SecureHandler;
import com.data.share.utils.MapUtils;
import com.data.share.utils.RequestUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * zuul前置过滤器
 *
 * @author jiajun_chen palading_cr@163.com
 */
@Component
public class ReqPreFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(ReqPreFilter.class);


    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    public boolean shouldFilter() {
        return true;
    }

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 利用责任链对请求进行校验
     * 当handler.next()为false时退出
     *
     * @author jiajun_chen palading_cr@163.com
     * @date 2018/6/20-9:34
     */
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        try {
            String auth = request.getHeader("auth");
            SecureVO vo = new SecureVO(auth,
                    request.getParameter(ReqRouteFilter.CACHE_GROUP),
                    request.getParameter(ReqRouteFilter.CACHE_CODE),
                    RequestUtils.getIpAddress(request));
            //获取handler容器
            Map<String, SecureHandler> maps = HanderManagerFactory.BUFFER_STATIC_SECURE;
            //责任链执行handler
            for (Map.Entry<String, SecureHandler> item : maps.entrySet()) {
                SecureHandler handler = item.getValue();
                //处理需要校验的内容，并设置是否需要下一个handler执行
                handler.handleError(vo);
                //判断是否需要下个handler执行
                if (!handler.next()) {
                    //返回定义的错误消息
                    flushRes(ctx, handler.getSecureEnums().getValue());
                }
            }
            ctx.set("secure", vo);
            ctx.setResponseStatusCode(200);
            ctx.setSendZuulResponse(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    public void flushRes(RequestContext ctx, String errorMsg) {
        ctx.setResponseBody(errorMsg);
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(401);
        ctx.getResponse().setContentType("application/json;charset=UTF-8");
    }

    public class SecureVO {
        private String auth;
        private String group;
        private String code;

        public String getIpaddr() {
            return ipaddr;
        }

        public void setIpaddr(String ipaddr) {
            this.ipaddr = ipaddr;
        }

        private String ipaddr;

        public SecureVO(String auth, String group, String code, String ipaddr) {
            this.auth = auth;
            this.group = group;
            this.code = code;
            this.ipaddr = ipaddr;
        }

        public String getAuth() {
            return auth;
        }

        public void setAuth(String auth) {
            this.auth = auth;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

    }

}


