package com.data.share.enums;

/**
 * @author jiajun_chen palading_cr@163.com
 * @title RespEnums
 * @project collect
 * @date 2018/6/20-15:23
 */
public enum RespEnums {
    SUCESS("0000","操作成功"),
    FAIL("9999","处理失败"),
    ERROR("1111","处理异常")
    ;

    private String code;

    private String msg;

    RespEnums(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
