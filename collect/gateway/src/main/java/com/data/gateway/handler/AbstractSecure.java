package com.data.gateway.handler;

import com.data.share.component.secure.SecureHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 安全处理接口基类
 * 用于安全接口公共处理逻辑
 *
 * @author jiajun_chen palading_cr@163.com
 */
public abstract class AbstractSecure implements SecureHandler {

    public static final Logger logger = LoggerFactory.getLogger(AbstractSecure.class);

    private boolean next;

    public boolean isNext() {
        return next;
    }

    public void setNext(boolean next) {
        this.next = next;
    }

    public boolean next() {
        return next;
    }

    public RestTemplate setRedis(RestTemplate template) {
        return null;
    }
}
