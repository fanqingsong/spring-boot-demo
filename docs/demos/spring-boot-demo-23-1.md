# spring-boot-demo-23-1：调用 REST 服务 - 如何使用代理

> 工程目录：[`spring-boot-demo-23-1`](../../spring-boot-demo-23-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **调用 REST 服务 - 如何使用代理** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- HttpClient / RestTemplate 调用第三方 HTTP API
- `RestRoncooController` 演示 POST JSON、GET 路径参数
- 与 02-1 提供 REST 相对：本讲是消费远程服务

## 代码实战（对照源码）

### 1. `RestRoncooController`

`@RequestBody JsonNode` 解析 JSON；结合 `RoncooUserLogCache` 更新数据。

### 2. httpclient 依赖

配置代理或连接池（视项目配置）。

## 运行与验证

- curl 模拟调用 `/rest/update` 等接口，观察日志。

## 动手练习

- 封装统一 RestTemplate Bean，设置超时与错误处理。


## 关键源码路径

按下面路径在 IDE 中打开对照（相对各 demo 工程根目录）：

**Java**

- `src/main/java/com/roncoo/example/SpringBootDemo231Application.java`
- `src/main/java/com/roncoo/example/bean/RoncooUser.java`
- `src/main/java/com/roncoo/example/bean/RoncooUserLog.java`
- `src/main/java/com/roncoo/example/cache/RoncooUserLogCache.java`
- `src/main/java/com/roncoo/example/cache/impl/RoncooUserLogCacheImpl.java`
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

- 上一模块：[`spring-boot-demo-22-1`](./spring-boot-demo-22-1.md)
- 下一模块：[`spring-boot-demo-24-1`](./spring-boot-demo-24-1.md)
