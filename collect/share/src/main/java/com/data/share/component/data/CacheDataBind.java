package com.data.share.component.data;

import java.io.Serializable;

/**
 * @author jiajun_chen palading_cr@163.com
 * @project collect
 * @date 2018/6/20-14:17
 */
public class CacheDataBind extends DataBind implements Serializable {

    private String key;

    private String result;

    private String group;

    private String ipaddr;

    private String code;

    private String identify;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getIpaddr() {
        return ipaddr;
    }

    public void setIpAddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }
}
