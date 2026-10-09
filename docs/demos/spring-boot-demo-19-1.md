# spring-boot-demo-19-1：使用 Caching - EhCache

> 工程目录：[`spring-boot-demo-19-1`](../../spring-boot-demo-19-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **使用 Caching - EhCache** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

本节每个知识点从 **为何出现**、**解决什么问题**、**若没有会怎样** 三方面说明，便于理解「为什么要学这一项」，而不只是记名词。

- **`@EnableCaching`**
  - **为何出现**：重复读库是常见性能瓶颈；缓存抽象要统一接口，否则每个 Service 自己 Map 乱搞。
  - **解决什么问题**：打开 Spring Cache，后续 `@Cacheable` 由 CacheManager 统一后端（EhCache）。
  - **若没有会怎样**：手写 HashMap 缓存无 TTL、线程不安全、多实例不一致。

- **`@Cacheable` / `@CachePut` / `@CacheEvict`**
  - **为何出现**：读多写少场景需要「查过就记、改过就更、删过就清」的一致策略。
  - **解决什么问题**：注解声明缓存语义，AOP 拦截方法，减少样板 if-cache-else-db。
  - **若没有会怎样**：更新了 DB 忘了清缓存，用户看到过期数据，投诉「我明明改过了」。

- **EhCache 本地 JVM 缓存**
  - **为何出现**：单节点应用想要零依赖、极低延迟的进程内缓存。
  - **解决什么问题**：EhCache 在 JVM 内命中，纳秒~微秒级，配置 heap/disk 层级。
  - **若没有会怎样**：没有本地缓存时，热点 id 每次打 DB，DB CPU 飙高。

- **`RoncooUserLogCacheImpl`**
  - **为何出现**：需要在真实 DAO 之上加一层，观察第二次查询是否还访问数据库。
  - **解决什么问题**：Cache 实现包装 DAO，日志对比第一次/第二次 SQL，验证缓存生效。
  - **若没有会怎样**：缓存加错层（Controller 缓存 Request）或 key 不对，以为开了缓存其实没效果。

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
