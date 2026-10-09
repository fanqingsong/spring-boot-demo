# spring-boot-demo-15-1：使用 SQL 关系型数据库 - 事务处理

> 工程目录：[`spring-boot-demo-15-1`](../../spring-boot-demo-15-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **使用 SQL 关系型数据库 - 事务处理** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

本节每个知识点从 **为何出现**、**解决什么问题**、**若没有会怎样** 三方面说明，便于理解「为什么要学这一项」，而不只是记名词。

- **`@Transactional`**
  - **为何出现**：数据库支持 ACID，但 Java 里若不用事务边界，多条 SQL 各自提交会产生中间态。
  - **解决什么问题**：声明式事务：方法内全部成功才 commit，任异常 rollback，保证一致性。
  - **若没有会怎样**：用户插入成功、日志插入失败，库内留下孤儿用户，对账与审计失败。

- **多表写入原子性**
  - **为何出现**：注册类业务常写主表+日志/积分等多表，业务上要求「要么全有要么全无」。
  - **解决什么问题**：同一 `@Transactional` 方法内顺序 insert，对外表现为一个原子操作。
  - **若没有会怎样**：半成功数据需人工或补偿脚本清理，线上纠纷与合规风险。

- **抛异常验证回滚**
  - **为何出现**：初学者不信注解真会回滚，需要可复现实验。
  - **解决什么问题**：故意在第二步抛 RuntimeException，查库应无第一条记录，建立直觉。
  - **若没有会怎样**：以为 try-catch 吞掉异常仍算成功，或误以为只读方法也要乱加事务，设计混乱。

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
