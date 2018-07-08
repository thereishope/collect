package com.data.gateway.fallBack;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.data.share.utils.JsonUtils;
import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;


/**
 * @author jiajun_chen palading_cr@163.com
 * @title AbstractCommonFallBackProvider
 * @project collect
 * @date 2018/6/29
 */
public class AbstractCommonFallBackProvider implements
        ClientHttpResponse {

    private String routeName;

    public String getRoute() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public HttpStatus getStatusCode() throws IOException {
        return HttpStatus.OK;
    }

    public int getRawStatusCode() throws IOException {
        return 200;
    }

    public String getStatusText() throws IOException {
        return HttpStatus.OK.toString();
    }

    public void close() {

    }

    /**熔断提示
      *@author jiajun_chen palading_cr@163.com
      *@date 2018/6/29
      *
      */
    public InputStream getBody() throws IOException {
        return new ByteArrayInputStream(
                JsonUtils.toJson(getRoute().concat("-SERVER FAIL")).getBytes());
    }

}
