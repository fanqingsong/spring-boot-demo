# spring-boot-demo-20-1：使用 Caching - Redis

> 工程目录：[`spring-boot-demo-20-1`](../../spring-boot-demo-20-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **使用 Caching - Redis** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- **Redis 集中式缓存**：缓存条目存在 Redis，多应用实例共享同一 key 空间，一台机器写入后其它实例也能命中，适合集群部署。
- **`RedisCacheConfiguration`**：定制 Spring Cache 使用的 RedisCacheManager（序列化方式、key 前缀、默认 TTL 等），把注解缓存落到 Redis。
- **与 19-1 EhCache 对比**：本地缓存简单、无网络开销；Redis 缓存需运维 Redis 但支持水平扩展与统一失效，按部署形态选型。

## 代码实战（对照源码）

### 1. `RedisCacheConfiguration`

配置 Redis 缓存管理器与序列化。

### 2. 缓存注解用法

与 19-1 相同，底层换 Redis。

## 运行与验证

- 多开实例验证缓存共享（同一 key 命中）。

## 动手练习

- 设置 cache 默认过期时间。


## 关键代码说明

`@Cacheable`、`@CachePut`、`@CacheEvict` 仍在 `RoncooUserLogCacheImpl`，和 19-1 相同。本讲换成 Redis 当 CacheManager，并自定义过期时间和 key。

```java
@Configuration
public class RedisCacheConfiguration extends CachingConfigurerSupport {

    @Bean
    public CacheManager cacheManager(RedisTemplate<?, ?> redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        cacheManager.setDefaultExpiration(20);
        Map<String, Long> expires = new HashMap<String, Long>();
        expires.put("roncooCache", 200L);
        cacheManager.setExpires(expires);
        return cacheManager;
    }

    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                StringBuilder sb = new StringBuilder();
                sb.append(o.getClass().getName());
                sb.append(method.getName());
                for (Object obj : objects) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }
}
```

- 默认过期 20 秒；名为 `roncooCache` 的缓存单独 200 秒，对应 19-1 里 EhCache 的 `timeToIdleSeconds`。
- 自定义 `KeyGenerator` 把「类名 + 方法名 + 每个参数」拼成 key，避免不同方法共用一个 id 时互相覆盖。
- `RoncooUserLogCacheImpl` 上写了 `key = "#p0"` 时，以注解里的 key 为准，不会走这个 `KeyGenerator`。只有没写 `key` 的缓存方法才用它。

## 关键源码路径

按下面路径在 IDE 中打开对照（相对各 demo 工程根目录）：

**Java**

- `src/main/java/com/roncoo/example/SpringBootDemo201Application.java`
- `src/main/java/com/roncoo/example/bean/RoncooUser.java`
- `src/main/java/com/roncoo/example/bean/RoncooUserLog.java`
- `src/main/java/com/roncoo/example/cache/RoncooUserLogCache.java`
- `src/main/java/com/roncoo/example/cache/impl/RoncooUserLogCacheImpl.java`
- `src/main/java/com/roncoo/example/component/RoncooMongodbComponent.java`
- `src/main/java/com/roncoo/example/component/RoncooRedisComponent.java`
- `src/main/java/com/roncoo/example/controller/ApiController.java`
- `src/main/java/com/roncoo/example/controller/FileController.java`
- `src/main/java/com/roncoo/example/controller/WebController.java`
- `src/main/java/com/roncoo/example/dao/RoncooUserDao.java`
- `src/main/java/com/roncoo/example/dao/RoncooUserLogDao.java`
- `src/main/java/com/roncoo/example/dao/RoncooUserLogMongoDao.java`
- `src/main/java/com/roncoo/example/dao/impl/RoncooUserDaoImpl.java`
- `src/main/java/com/roncoo/example/handler/BizExcepiton.java`
- `src/main/java/com/roncoo/example/service/UserService.java`
- `src/main/java/com/roncoo/example/util/base/JdbcDaoImpl.java`
- `src/main/java/com/roncoo/example/util/base/Page.java`
- `src/main/java/com/roncoo/example/util/base/Sql.java`
- `src/main/java/com/roncoo/example/util/configuration/CustomCorsConfiguration.java`
- `src/main/java/com/roncoo/example/util/configuration/CustomCorsConfiguration2.java`
- `src/main/java/com/roncoo/example/util/configuration/RedisCacheConfiguration.java`
- `src/main/java/com/roncoo/example/util/filter/CustomFilter.java`
- `src/main/java/com/roncoo/example/util/listerner/CustomListener.java`
- `src/main/java/com/roncoo/example/util/servlet/CustomServlet.java`

**配置/模板**

- `src/main/resources/application-dev.properties`
- `src/main/resources/application-prod.properties`
- `src/main/resources/application-test.properties`
- `src/main/resources/application.properties`
- `src/main/resources/config/ehcache.xml`
- `src/main/resources/logback-roncoo.xml`
- `src/main/resources/public/error/404.html`
- `src/main/resources/templates/error/500.ftl`
- `src/main/resources/templates/error/5xx.ftl`
- `src/main/resources/templates/error/error.ftl`
- `src/main/resources/templates/index.ftl`
## 学习路径

- 上一模块：[`spring-boot-demo-19-1`](./spring-boot-demo-19-1.md)
- 下一模块：[`spring-boot-demo-21-1`](./spring-boot-demo-21-1.md)
