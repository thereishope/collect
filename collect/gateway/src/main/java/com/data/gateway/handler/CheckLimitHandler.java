package com.data.gateway.handler;

import com.data.gateway.filter.ReqPreFilter;
import com.data.share.enums.SecureEnums;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import static com.data.gateway.proxy.CacheRequest.CACHE_LIMIT_REQUEST;

/**请求次数调用
 * @author jiajun_chen palading_cr@163.com
 * @title CheckLimitHandler
 * @project collect
 * @date 2018/6/20-13:39
 */
@Component
public class CheckLimitHandler extends AbstractSecure {

    @Value("${cache.request.limit}")
    private int limit;


    @Autowired
    private RedisTemplate redisTemplate;

    /**验证请求是否在指定时间内调用次数过多
      *@methed
      *@return
      *@author jiajun_chen palading_cr@163.com
      *@date 2018/6/21
      *
      */
    public <T> void handleError(T t) {
        ReqPreFilter.SecureVO vo = (ReqPreFilter.SecureVO) t;
        int count = 0;
        try {
            String key = CACHE_LIMIT_REQUEST.
                    concat("-").concat(vo.getAuth()).concat("-").
                    concat(vo.getGroup()).concat("-").concat(vo.getCode());
            Object o = redisTemplate.opsForValue().get(key);
            if (null != o) count = (Integer) o;
        } catch (Exception e) {
            logger.error("CheckLimitHandler[handleError]发生异常", e);
        }
        setNext(count < limit);
    }

    public String getCode() {
        return getSecureEnums().getCode();
    }

    public SecureEnums getSecureEnums() {
        return SecureEnums.SECURE_1000;
    }
}
