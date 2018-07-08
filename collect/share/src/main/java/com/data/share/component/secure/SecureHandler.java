package com.data.share.component.secure;

import com.data.share.enums.SecureEnums;
import org.springframework.web.client.RestTemplate;

/**
 * 安全处理接口
 *
 * @author jiajun_chen palading_cr@163.com
 */
public interface SecureHandler {


    /**对pre阶段的请求进行拦截处理
      *@author jiajun_chen palading_cr@163.com
      *@date 2018/6/22
      *
      */
    public <T> void handleError(T t);

    /**获取业务码
      *@author jiajun_chen palading_cr@163.com
      *@date 2018/6/22
      *
      */
    public String getCode();

    /**通过handleError方法执行next逻辑
      *@author jiajun_chen palading_cr@163.com
      *@date 2018/6/22
      *
      */
    public boolean next();

    /**
     * 业务枚举
     * @return
     */
    public SecureEnums getSecureEnums();

}
