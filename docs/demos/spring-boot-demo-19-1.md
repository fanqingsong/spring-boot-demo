# spring-boot-demo-19-1：使用 Caching - EhCache

> 工程目录：[`spring-boot-demo-19-1`](../../spring-boot-demo-19-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **使用 Caching - EhCache** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- `@EnableCaching` 开启缓存抽象
- `@Cacheable` / `@CachePut` / `@CacheEvict`
- EhCache 作本地 JVM 缓存
- `RoncooUserLogCacheImpl` 在 DAO 之上加缓存层

## 代码实战（对照源码）

### 1. `SpringBootDemo191Application`

启用 `@EnableCaching`。

### 2. `RoncooUserLogCacheImpl`

select 走 `@Cacheable`，update `@CachePut`，delete `@CacheEvict`；观察第二次查询是否不打库。

## 运行与验证

- 连续两次请求同一 id，控制台仅第一次打印读库日志。

## 动手练习

- 配置 ehcache.xml 限制缓存条目与过期时间。


## 关键代码说明

缓存要先在启动类打开，否则 `@Cacheable` 不生效：

```java
@EnableCaching
@SpringBootApplication
public class SpringBootDemo191Application {
```

缓存实现用 EhCache，配置指向：

```properties
spring.cache.ehcache.config=classpath:config/ehcache.xml
```

```xml
<cache name="roncooCache" eternal="false" maxEntriesLocalHeap="0" timeToIdleSeconds="200"/>
```

`name` 必须和 `@CacheConfig(cacheNames = "roncooCache")` 一致。空闲超过 200 秒的条目会被清掉。

```java
@CacheConfig(cacheNames = "roncooCache")
@Repository
public class RoncooUserLogCacheImpl implements RoncooUserLogCache {

    @Cacheable(key = "#p0")
    public RoncooUserLog selectById(Integer id) {
        System.out.println("查询功能，缓存找不到，直接读库, id=" + id);
        return roncooUserLogDao.findOne(id);
    }

    @CachePut(key = "#p0")
    public RoncooUserLog updateById(RoncooUserLog roncooUserLog) {
        return roncooUserLogDao.save(roncooUserLog);
    }

    @CacheEvict(key = "#p0")
    public String deleteById(Integer id) {
        return "清空缓存成功";
    }
}
```

- `#p0` 是第一个参数。`selectById(1)` 的缓存键是 `1`。
- `@Cacheable`：命中则直接返回缓存，**不会进入方法体**，控制台看不到「直接读库」。未命中才查库，并把返回值写入缓存。
- `@CachePut`：方法总会执行，再用返回值更新键 `#p0`。这里第一个参数是整个 `RoncooUserLog` 对象，键是对象的 `toString()`，和查询时的 id 不是同一个键，更新后再次 `selectById` 仍可能命中旧缓存。这是源码里的实际行为。
- `@CacheEvict`：按同样规则删键。`deleteById` 的方法体没有调用 DAO 删库，只删缓存并返回固定字符串。

## 关键源码路径

按下面路径在 IDE 中打开对照（相对各 demo 工程根目录）：

**Java**

- `src/main/java/com/roncoo/example/SpringBootDemo191Application.java`
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

- 上一模块：[`spring-boot-demo-18-1`](./spring-boot-demo-18-1.md)
- 下一模块：[`spring-boot-demo-20-1`](./spring-boot-demo-20-1.md)
