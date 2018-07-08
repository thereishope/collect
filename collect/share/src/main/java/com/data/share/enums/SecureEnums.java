package com.data.share.enums;

/**安全处理枚举
 * @author jiajun_chen palading_cr@163.com
 */
public enum SecureEnums {

    SECURE_1000("secure_1000","调用次数限制超出限制"),
    SECURE_1004("secure_1004","auth校验不通过"),
    SECURE_1007("secure_1007","参数校验不通过");

    private String code;
    private String value;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    SecureEnums(String code, String value) {
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
