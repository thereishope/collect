package com.data.gateway.handler;

import com.data.gateway.filter.ReqPreFilter;
import com.data.share.enums.SecureEnums;
import com.data.share.exceptions.BuisException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author jiajun_chen palading_cr@163.com
 * @title ParamSecureHandler
 * @project collect
 * @date 2018/6/20-11:34
 */
@Component
public class ParamSecureHandler extends AbstractSecure {


    /**
     * 校验参数是否为空
     *
     * @return
     * @methed
     * @author jiajun_chen palading_cr@163.com
     * @date 2018/6/20
     */
    public <T> void handleError(T t) {
        ReqPreFilter.SecureVO vo = (ReqPreFilter.SecureVO) t;
        boolean flag = true;
        try {
            if (null == vo) {
                setNext(flag = false);
            } else {
                Class<?> clz = vo.getClass();
                Field[] fields = vo.getClass().getFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    Method m = (Method) vo.getClass().getMethod(
                            "get" + getMethodName(field.getName()));
                    String val = (String) m.invoke(vo);
                    if (StringUtils.isEmpty(val)) {
                        setNext(flag = false);
                        break;
                    }
                }
                setNext(true);
            }

        } catch (Exception e) {
            setNext(false);
            logger.error("ParamSecureHandler[handleError]发生异常", e);
        }
    }

    public String getCode() {
        return getSecureEnums().getCode();
    }

    public SecureEnums getSecureEnums() {
        return SecureEnums.SECURE_1007;
    }

    private String getMethodName(String fildeName) throws Exception {
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }
}
