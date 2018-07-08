package com.data.share.param;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jiajun_chen palading_cr@163.com
 * @title BindData
 * @project collect
 * @date 2018/6/29
 */
public class BindData implements Serializable {

    public Map<String, Object> param;


    public BindData(Map<String, Object> param) {
        this.param = param;
    }

    public BindData() {
        this.param = new HashMap<String, Object>();
    }

    /**
      *@author jiajun_chen palading_cr@163.com
      *@date 2018/6/29
      *
      */
    public <T> T get(String serKey, Class<T> clazz) {
        return (T) this.param.get(serKey);
    }

    public void set(String serKey, Object o) {
        this.param.put(serKey, o);
    }


    public void removeAttr(String key) {
        this.param.remove(key);
    }

   
    public void clear() {
        this.param.clear();
    }


}
