package com.data.share.component.data;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author jiajun_chen palading_cr@163.com
 * @title Bind
 * @project collect
 * @date 2018/6/20-14:16
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({@JsonSubTypes.Type(value=DataBind.class,name = "dataBind"),@JsonSubTypes.Type(value=CacheDataBind.class,name = "bind")})
public interface Bind {

    public String getKey();

    public String getResult();

    public Bind getBind(Bind bind);

    public String getGroup();

    public String getIpaddr();

    public String getCode();

    public String getIdentify();

    public void setResult(String result);
}
