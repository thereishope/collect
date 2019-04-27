# collect
基于spring cloud的基础缓存拉取系统
### 主要功能
- 通过api网关对请求在pre阶段进行链式拦截（认证，调用次数限制，参数校验等）,在route阶段通过代理重试调用以及异步执行黑名单等逻辑
- server端启动时加载缓存数据，您可以结合需求选择本地缓存或者分布式缓存实现
- 支持集群部署
- server端flush缓存后，利用MQ完成缓存消息推送(待完成)
### 特性介绍(client)
- 通过责任链在pre阶段进行拦截校验,您可以实现该接口扩展自己的需求实现

```java
public interface SecureHandler {


    /**对pre阶段的请求进行拦截处理
      *@author jiajun_chen palading_cr@163.com
      *@date 2018/6/22
      *
      */
    public <T> void handleError(T t);
    
    /**获取业务码
      *@author jiajun_chen palading_cr@163.com
      *@date 2018/6/22
      *
      */
    public String getCode();

    /**通过handleError方法执行next逻辑
      *@author jiajun_chen palading_cr@163.com
      *@date 2018/6/22
      *
      */
    public boolean next();

    /**
     * 业务枚举
     * @return
     */
    public SecureEnums getSecureEnums();

}
```
- 通过代理在route阶段完成对后端缓存请求，以及异步处理后置逻辑
```java
/**
 * 缓存请求代理
 *
 * @author jiajun_chen palading_cr@163.com
 */
public class CacheRequestProxy implements CacheRequest<BindData>{

    private static final Logger logger = LoggerFactory.getLogger(
            CacheRequestProxy.class);

    private CacheRequest cacheRequest;

    public CacheRequestProxy(CacheRequest cacheRequest) {
        this.cacheRequest = cacheRequest;
    }

    /**
     * 接收CacheDataBind子类参数
     * 发送请求并异步判断是否需要加入黑名单
     * @return
     * @methed
     * @author jiajun_chen palading_cr@163.com
     * @date 2018/6/20
     */
    public <T> T sendRequest(T t) {
        BindData bind = null;
        try {
            bind = (BindData) cacheRequest.sendRequest(t);
            tobackList(bind);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (T)bind;
    }

    /**
      *@methed
      *@return
      *@author jiajun_chen palading_cr@163.com
      *@date 2018/6/20
      *
      */
    public <T> void tobackList(T t) {
        HanderManagerFactory.
                BUFFER_STATIC_ASYNC.
                get(AsyncEnums.ASYNC_1000.getCode()).
                asyncHandle(t);
    }

    public int getCacheCount(String ipAddr) {
        return 0;
    }
}
   ```
- 当在请求发生异常时，您可以通过重试完成对server端调用
```java
/**通用请求基类
 * @author jiajun_chen palading_cr@163.com
 * @title AbstractCommonSender
 * @project collect
 * @date 2018/7/8
 */
public abstract  class AbstractCommonSender {


    private RestTemplate restTemplate;

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public static Map<String,Method> CACHE_METHOD = new ConcurrentHashMap<String,Method>();

    private static String PRIFX_NAME = "sendBy";


    /**发送请求
      *@author jiajun_chen palading_cr@163.com
      *@date 2018/7/8
      *
      */
    public  <T> T post(String url,T data,Class<T> clazz){
        T t = null;
        try {
             t = sendByPost(url,data,clazz);
        } catch (Exception e) {
           
            }
        }
        return t;
    }

    /**post请求
      *@author jiajun_chen palading_cr@163.com
      *@date 2018/7/8
      *
      */
    public <T> T sendByPost(String url,T data,Class<T> clazz)throws Exception{
        return restTemplate.postForObject(url,data,clazz);
    }

    /**get请求
      *@author jiajun_chen palading_cr@163.com
      *@date 2018/7/8
      *
      */
    public <T> T sendByGet(String url,Class<T> clazz)throws Exception{
        return restTemplate.getForObject(url,clazz);
    }

    /**将方法加入到缓存中
      *@author jiajun_chen palading_cr@163.com
      *@date 2018/7/8
      *
      */
    private Method get(String methodName){
        Method [] method =  this.getClass().getMethods();
        if(!CACHE_METHOD.containsKey(methodName)){
            for (Method met : method){
                if(met.getName().startsWith(PRIFX_NAME)){
                    CACHE_METHOD.put(met.getName(),met);
                }
            }
        }
        return CACHE_METHOD.get(methodName);

    }
}
```
  
  ### 特性介绍（server）
  - 您可以采用Map作为缓存容器实现，也可以采用诸如Redis,memecached等分布式缓存容器实现
  - Cache为缓存容器接口(本项目中的SimpleCache,RedisRemoteCache,MemecacheRemoteCache为该接口实现)，CacheInit则是具体缓存加载实现
  ```java
  
  /**分布式缓存实现
 * @author jiajun_chen palading_cr@163.com
 * @title RemoteCacheFactory
 * @project collect
 * @date 2018/6/20
 */
public class RemoteCacheFactory<K,V> implements CacheFactory<K,V> {

    private Cache<K, V> cache;

    private CacheInit<K, V> cacheInit;

    public RemoteCacheFactory(CacheInit<K, V> cacheInit,Cache cache) {
        this.cache = cache;
        this.cacheInit = cacheInit;
        initCache();
    }


    public void initCache() {
        this.cacheInit.init(this.cache);
    }



    public Cache<K, V> getCache() {
        return this.cache;
    }
}
```
- 在项目启动时，加载编写好的缓存实现，您可以根据自身需求，选择不同的缓存实现

 ```java
 public final class CacheHelper {

    private static RedisRemoteCache redisRemoteCache;

    private static MemecacheRemoteCache memecacheRemoteCache;

    static {
        redisRemoteCache = (RedisRemoteCache) ServerApplication.context.getBean("redisRemoteCache");
        memecacheRemoteCache = (MemecacheRemoteCache) ServerApplication.context.getBean("memecacheRemoteCache");
    }

    //缓存0000-0001
    public static final CacheFactory<String, Object> simpleCache =
            new SimpleCacheFactory<String, Object>(new Cache0000Service0001());

    //缓存0000-0002
    public static final CacheFactory<String, Object> redisCache =
            new RemoteCacheFactory(new RedisCache0000Service0002(), redisRemoteCache);

    //缓存0000-0003
    public static final CacheFactory<String, Object> memCache =
            new RemoteCacheFactory(new Memcache0000Service0003(), memecacheRemoteCache);
```
  
  - 缓存服务端启动后加载服务映射关系并存入Redis，因为存入的是group和code，所以并不影响server端的服务集群。
  ```java
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
```
