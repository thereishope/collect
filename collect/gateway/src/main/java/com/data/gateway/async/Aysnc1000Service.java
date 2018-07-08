package com.data.gateway.async;

import com.data.share.component.async.AsyncHandler;
import com.data.share.component.data.CacheDataBind;
import com.data.share.enums.AsyncEnums;
import org.springframework.stereotype.Component;

/**黑名单
 * @author jiajun_chen palading_cr@163.com
 * @title Aysnc1000Service
 * @project collect
 * @date 2018/6/20-16:37
 */
@Component
public class Aysnc1000Service implements AsyncHandler {

    public Aysnc1000Service() {
        super();
    }

    /**黑名单逻辑
      *@methed
      *@return
      *@author jiajun_chen palading_cr@163.com
      *@date 2018/6/20
      *
      */
    public <T> void asyncHandle(T t) {
        CacheDataBind bind = (CacheDataBind)t;
/*
        String requestUrl = bind.getIpaddr();
*/
    }

    public String getCode() {
        return AsyncEnums.ASYNC_1000.getCode();
    }
}
