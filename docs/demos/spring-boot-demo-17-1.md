# spring-boot-demo-17-1：使用 NoSQL 数据库 - Redis

> 工程目录：[`spring-boot-demo-17-1`](../../spring-boot-demo-17-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **使用 NoSQL 数据库 - Redis** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- `spring-boot-starter-data-redis`
- `RedisTemplate` 或封装 Component 操作键值
- 与关系库并存：业务数据 MySQL/H2，缓存/会话放 Redis

## 代码实战（对照源码）

### 1. `RoncooRedisComponent`

封装 set/get 等常用操作。

### 2. `ApiController`

通过 HTTP 触发 Redis 读写。

## 运行与验证

- 启动 Redis，配置 host/port；调用 API 后在 redis-cli 验证 key。

## 动手练习

- 实现 TTL 过期；缓存用户对象 JSON 序列化。


## 关键代码说明

连接信息在 `application-dev.properties`：

```properties
spring.redis.host=localhost
spring.redis.port=6379
```

有这些属性且引入 Redis starter 后，容器里会有 `StringRedisTemplate`。业务代码不自己 `new` 连接。

```java
@Component
public class RoncooRedisComponent {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void set(String key, String value) {
        ValueOperations<String, String> ops = this.stringRedisTemplate.opsForValue();
        if (!this.stringRedisTemplate.hasKey(key)) {
            ops.set(key, value);
        } else {
            System.out.println("this key = " + ops.get(key));
        }
    }

    public String get(String key) {
        return this.stringRedisTemplate.opsForValue().get(key);
    }

    public void del(String key) {
        this.stringRedisTemplate.delete(key);
    }
}
```

- `opsForValue()` 操作字符串类型。键已存在时 `set` 不会覆盖，只打印旧值，这是示例逻辑，不是 Redis 的默认行为。
- `get` 键不存在时返回 `null`。
- `StringRedisTemplate` 的键和值都是 String。要存对象需要 `RedisTemplate` 配序列化器，本类没有做这件事。

## 关键源码路径

按下面路径在 IDE 中打开对照（相对各 demo 工程根目录）：

**Java**

- `src/main/java/com/roncoo/example/SpringBootDemo171Application.java`
- `src/main/java/com/roncoo/example/bean/RoncooUser.java`
- `src/main/java/com/roncoo/example/bean/RoncooUserLog.java`
- `src/main/java/com/roncoo/example/component/RoncooRedisComponent.java`
- `src/main/java/com/roncoo/example/controller/ApiController.java`
- `src/main/java/com/roncoo/example/controller/FileController.java`
- `src/main/java/com/roncoo/example/controller/WebController.java`
- `src/main/java/com/roncoo/example/dao/RoncooUserDao.java`
- `src/main/java/com/roncoo/example/dao/RoncooUserLogDao.java`
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
- `src/main/resources/logback-roncoo.xml`
- `src/main/resources/public/error/404.html`
- `src/main/resources/templates/error/500.ftl`
- `src/main/resources/templates/error/5xx.ftl`
- `src/main/resources/templates/error/error.ftl`
- `src/main/resources/templates/index.ftl`
## 学习路径

- 上一模块：[`spring-boot-demo-16-1`](./spring-boot-demo-16-1.md)
- 下一模块：[`spring-boot-demo-18-1`](./spring-boot-demo-18-1.md)
