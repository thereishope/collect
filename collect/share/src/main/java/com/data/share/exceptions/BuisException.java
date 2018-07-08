package com.data.share.exceptions;

/**
 * @author jiajun_chen palading_cr@163.com
 * @title BuisException
 * @project collect
 * @date 2018/6/20-11:42
 */
public class BuisException extends Exception {

    private String code;
    private String msg;

    public BuisException(String code, String msg) {
        super(msg);
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

    public BuisException() {
        super();
    }

}
