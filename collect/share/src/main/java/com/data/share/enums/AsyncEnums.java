package com.data.share.enums;

/**异步处理枚举
 * @author jiajun_chen palading_cr@163.com
 */
public enum AsyncEnums {

    ASYNC_1000("async_1000","异步统计ip调用次数");

    private String code;
    private String value;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    AsyncEnums(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
