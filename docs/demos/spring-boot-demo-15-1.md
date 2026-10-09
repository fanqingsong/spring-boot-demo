# spring-boot-demo-15-1：使用 SQL 关系型数据库 - 事务处理

> 工程目录：[`spring-boot-demo-15-1`](../../spring-boot-demo-15-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **使用 SQL 关系型数据库 - 事务处理** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- **`@Transactional`**：声明式事务，方法内多条数据库操作在同一连接/事务中执行；任一步失败则整体回滚，保证数据一致。
- **多表写入原子性**：注册时写用户表再写日志表，要么两条都成功要么都不留，避免出现「有用户无日志」的脏数据。
- **抛异常验证回滚**：注释/打开 `UserService.register` 中的 `RuntimeException`，可观察第二次调用后库中无半成功记录，理解默认回滚规则。

## 代码实战（对照源码）

### 1. `UserService.register`

先 `roncooUserDao.insert` 再写日志，方法上加 `@Transactional`。

### 2. `ApiController`

暴露注册 HTTP 接口。

## 运行与验证

- 正常注册后查库两条记录；打开 throw RuntimeException 再测，应无脏数据。

## 动手练习

- 配置只读事务 `@Transactional(readOnly=true)` 用于查询方法。


## 关键代码说明

注册要写两张表：用户用 JdbcTemplate，日志用 JPA。`@Transactional` 把两次写入放进同一个事务。

```java
@Service
public class UserService {

    @Autowired
    private RoncooUserDao roncooUserDao;
    @Autowired
    private RoncooUserLogDao roncooUserLogDao;

    @Transactional
    public String register(String name, String ip) {
        RoncooUser roncooUser = new RoncooUser();
        roncooUser.setName(name);
        roncooUser.setCreateTime(new Date());
        roncooUserDao.insert(roncooUser);

        RoncooUserLog roncooUserLog = new RoncooUserLog();
        roncooUserLog.setUserName(name);
        roncooUserLog.setUserIp(ip);
        roncooUserLog.setCreateTime(new Date());
        roncooUserLogDao.save(roncooUserLog);
        return "success";
    }
}
```

- 事务加在 Service 上，而不是 DAO。一次业务（注册）对应一个事务边界。
- 方法正常返回时提交：`insert` 和 `save` 都落库。
- 方法抛出运行时异常时回滚。源码里留了一段注释掉的 `throw new RuntimeException()`，打开后用户插入也会被回滚，两张表都不应出现这次注册的数据。
- 调用必须经过 Spring 代理（其它 Bean 注入 `UserService` 再调用）。同类内部 `this.register(...)` 不会启动事务。

## 关键源码路径

按下面路径在 IDE 中打开对照（相对各 demo 工程根目录）：

**Java**

- `src/main/java/com/roncoo/example/SpringBootDemo151Application.java`
- `src/main/java/com/roncoo/example/bean/RoncooUser.java`
- `src/main/java/com/roncoo/example/bean/RoncooUserLog.java`
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

- 上一模块：[`spring-boot-demo-14-1`](./spring-boot-demo-14-1.md)
- 下一模块：[`spring-boot-demo-16-1`](./spring-boot-demo-16-1.md)
