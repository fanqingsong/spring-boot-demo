# spring-boot-demo-25-1：使用 Spring Session 实现集群 - Redis

> 工程目录：[`spring-boot-demo-25-1`](../../spring-boot-demo-25-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **使用 Spring Session 实现集群 - Redis** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- Session 存 Redis，多 Tomcat 实例共享登录态
- `spring-session` + Redis
- 无需 sticky session 即可水平扩展

## 代码实战（对照源码）

### 1. 依赖与配置

Redis 连接与 session namespace。

### 2. 业务 Controller

写入 session 属性，模拟登录。

## 运行与验证

- 两个不同端口实例 + 同一 Redis，验证 session 共享（按视频完整步骤）。

## 动手练习

- 对比 Cookie 中 SESSION ID 变化。


## 关键源码路径

按下面路径在 IDE 中打开对照（相对各 demo 工程根目录）：

**Java**

- `src/main/java/com/roncoo/example/SpringBootDemo251Application.java`
- `src/main/java/com/roncoo/example/bean/RoncooUser.java`
- `src/main/java/com/roncoo/example/bean/RoncooUserLog.java`
- `src/main/java/com/roncoo/example/cache/RoncooUserLogCache.java`
- `src/main/java/com/roncoo/example/cache/impl/RoncooUserLogCacheImpl.java`
- `src/main/java/com/roncoo/example/component/RoncooJavaMailComponent.java`
- `src/main/java/com/roncoo/example/component/RoncooMongodbComponent.java`
- `src/main/java/com/roncoo/example/component/RoncooRedisComponent.java`
- `src/main/java/com/roncoo/example/controller/ApiController.java`
- `src/main/java/com/roncoo/example/controller/FileController.java`
- `src/main/java/com/roncoo/example/controller/RestRoncooController.java`
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
- `src/main/java/com/roncoo/example/util/configuration/RoncooJavaMailSenderImpl.java`
- `src/main/java/com/roncoo/example/util/filter/CustomFilter.java`
- `... 共 27 个文件`

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
- `src/main/resources/templates/mail/roncoo.ftl`
## 学习路径

- 上一模块：[`spring-boot-demo-24-1`](./spring-boot-demo-24-1.md)
- 下一模块：[`spring-boot-demo-26-1`](./spring-boot-demo-26-1.md)
