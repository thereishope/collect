package com.data.gateway.fallBack;

import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**通用熔断
 * @author jiajun_chen palading_cr@163.com
 * @title CommonFallBackProvider
 * @project collect
 * @date 2018/6/29
 */
@Component
public class CommonFallBackProvider extends AbstractCommonFallBackProvider
        implements ZuulFallbackProvider {

    @Value("${zuul.prefix}")
    private String API_PREFIX;


    public ClientHttpResponse fallbackResponse() {
        return this;
    }

    /**获取熔断路由
      *@author jiajun_chen palading_cr@163.com
      *@date 2018/6/29
      *
      */
    public String getRoute() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        if (null == request) {
            return null;
        }
        String url = request.getRequestURI();
        String SERVER_ROUTE = url.split(API_PREFIX+"/")[1].split("/",0)[0];
        super.setRouteName(SERVER_ROUTE);
        return SERVER_ROUTE;
    }
}
