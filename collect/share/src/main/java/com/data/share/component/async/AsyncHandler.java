package com.data.share.component.async;

/**异步处理接口
 * @author jiajun_chen palading_cr@163.com
 */
public interface AsyncHandler {

    public <T> void asyncHandle(T t);

    public String getCode();

}
