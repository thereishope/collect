package com.data.gateway.handler;

import com.data.gateway.filter.ReqPreFilter;
import com.data.share.enums.SecureEnums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**auth安全处理
 * @author jiajun_chen palading_cr@163.com
 */
@Component
public class AuthSecureHandler extends AbstractSecure {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 验证auth内容的正确性,
     * 并根据验证结果设置isNext
     * @param t
     * @param <T>
     */
    public <T> void handleError(T t) {
        ReqPreFilter.SecureVO auth = (ReqPreFilter.SecureVO)t;
        //可以根据自己的需求进行验证，本例子中默认只校验auth是否匹配
        String cacheAuth = (String)redisTemplate.opsForValue().get("auth");
        this.setNext(cacheAuth.equals(auth.getAuth()));

    }

    public String getCode() {
        return getSecureEnums().getCode();
    }

    public SecureEnums getSecureEnums() {
        return SecureEnums.SECURE_1004;
    }
}
