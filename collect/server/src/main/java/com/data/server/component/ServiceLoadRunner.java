package com.data.server.component;

import com.data.server.controller.CommandLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**监听器完成服务与key的映射
 * @author jiajun_chen palading_cr@163.com
 * @title ServiceLoadRunner
 * @project collect
 * @date 2018/6/29
 */
@Component
public class ServiceLoadRunner implements CommandLineRunner {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private List<CommandLoader> loaders;

    @Value("${spring.application.name}")
    private String server;


    /**加载服务与key之间的映射，如果存在，则不加载
      *@author jiajun_chen palading_cr@163.com
      *@date 2018/6/29
      *
      */
    public void run(String... strings) throws Exception {
        loaders.forEach(e -> {
            String group = e.getClass().getAnnotation(
                    RestController.class).value();
            Method[] methods = e.getClass().getMethods();
            for (Method method : methods) {
                Annotation annotation = method.
                        getAnnotation(RequestMapping.class);
                String key = group.concat("_").
                        concat(((RequestMapping) annotation).value()[0]);
                redisTemplate.getConnectionFactory().getConnection().
                        setNX(key.getBytes(), server.getBytes());
            }

        });
    }
}
