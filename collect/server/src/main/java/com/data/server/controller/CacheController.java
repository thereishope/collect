package com.data.server.controller;

import com.data.server.component.CacheHelper;
import com.data.share.component.data.Bind;
import com.data.share.component.data.CacheDataBind;
import com.data.share.param.BindData;
import com.data.share.utils.JsonUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jiajun_chen palading_cr@163.com
 * @title CacheController
 * @project collect
 * @date 2018/6/20-17:25
 */
@RestController("0000")
public class CacheController extends AbstractController{

    private static final String CACHE_PREFIX = "cache";

    /**
     * 简单缓存实现
     *
     * @return
     * @methed
     * @author jiajun_chen palading_cr@163.com
     * @date 2018/6/21
     */
    @RequestMapping("0001")
    public BindData getCache(HttpServletRequest request, @RequestBody BindData bind) {
        Object o = CacheHelper.
                simpleCache.
                getCache().
                get(CACHE_PREFIX.concat("-").concat(bind.get("group",String.class)).concat("-").concat(bind.get("code",String.class)));
        bind.set("result",JsonUtils.toJson(o));
        return bind;
    }

    /**
     * redis缓存实现
     *
     * @return
     * @methed
     * @author jiajun_chen palading_cr@163.com
     * @date 2018/6/21
     */
    @RequestMapping("0002")
    public BindData getRedisCache(HttpServletRequest request, @RequestBody BindData bind) {
        Object o = CacheHelper.
                redisCache.
                getCache().
                get(CACHE_PREFIX.concat("-").concat(bind.get("group",String.class)).concat("-").concat(bind.get("code",String.class)));
        bind.set("result",JsonUtils.toJson(o));
        return bind;
    }


    /**
     * redis缓存实现
     *
     * @return
     * @methed
     * @author jiajun_chen palading_cr@163.com
     * @date 2018/6/21
     */
    @RequestMapping("0003")
    public BindData getMemCache(HttpServletRequest request, @RequestBody BindData bind) {
        Object o = CacheHelper.
                memCache.
                getCache().
                get(CACHE_PREFIX.concat("-").concat(bind.get("group",String.class)).concat("-").concat(bind.get("code",String.class)));
        bind.set("result",JsonUtils.toJson(o));
        return bind;

    }
}
