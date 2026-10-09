# spring-boot-demo-27-1：生产准备 - 基于 HTTP 的监控

> 工程目录：[`spring-boot-demo-27-1`](../../spring-boot-demo-27-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **生产准备 - 基于 HTTP 的监控** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

本节每个知识点从 **为何出现**、**解决什么问题**、**若没有会怎样** 三方面说明，便于理解「为什么要学这一项」，而不只是记名词。

- **`spring-boot-starter-actuator`**
  - **为何出现**：上线后需要知道进程是否活着、依赖是否正常，K8s/监控依赖 HTTP 探针。
  - **解决什么问题**：暴露 health/metrics 等端点，自动化运维可拉取状态。
  - **若没有会怎样**：只能 tail 日志猜健康，宕机发现慢；K8s 无法配置 liveness/readiness。

- **`spring-boot-starter-security`**
  - **为何出现**：Actuator 早期默认暴露 env、shutdown 等，未授权访问等于把配置送给攻击者。
  - **解决什么问题**：对管理端点加认证，未登录 401，满足最小暴露原则。
  - **若没有会怎样**：health 公开还好，env 泄露数据库密码；或 shutdown 被扫到直接停服。

- **生产暴露策略**
  - **为何出现**：不是端点越多越好，而是「必要 + 受控」。
  - **解决什么问题**：只开放 health/info 等，敏感端点关闭或网络隔离，本模块演示鉴权行为。
  - **若没有会怎样**：全开 actuator 公网可访问，合规审计不通过，一次扫描即信息泄露。

## 代码实战（对照源码）

### 1. 安全配置

Actuator 路径权限、用户密码。

### 2. application

management 端点暴露策略（视版本配置键）。

## 运行与验证

- 访问 health 或配置的管理路径；未授权应 401。

## 动手练习

- 只暴露 health、info，关闭 env 等敏感端点。


## 关键代码说明

监控没有单独的 Java 类。引入 `spring-boot-starter-actuator` 后，框架按配置注册 HTTP 端点。开发配置里相关段落是：

```properties
endpoints.sensitive=true
endpoints.shutdown.enabled=true

security.basic.enabled=true
security.user.name=roncoo1
security.user.password=roncoo1
management.security.roles=SUPERUSER

security.basic.path=/manage
management.context-path=/manage
```

- `management.context-path=/manage` 把 Actuator 从业务端口的根路径挪到 `/manage`。例如健康检查是 `/manage/health`，而不是 `/health`。
- `endpoints.sensitive=true` 表示这些端点需要认证。`security.basic.enabled=true` 打开 HTTP Basic，用户名和密码就是上面两项。
- `security.basic.path=/manage` 只保护管理路径，业务接口 `/web/index` 不要求这套账号。
- `endpoints.shutdown.enabled=true` 会暴露关闭应用的端点。这是演示开关，生产环境不应打开。

1.4.x 的键名是 `management.context-path`、`endpoints.sensitive`。Spring Boot 2.x 改成了 `management.endpoints.web.base-path` 和 `management.endpoint.*.enabled`，升级时不能原样照搬。

## 关键源码路径

按下面路径在 IDE 中打开对照（相对各 demo 工程根目录）：

**Java**

- `src/main/java/com/roncoo/example/SpringBootDemo271Application.java`
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

- 上一模块：[`spring-boot-demo-26-1`](./spring-boot-demo-26-1.md)
- 下一模块：[`spring-boot-demo-28-1`](./spring-boot-demo-28-1.md)
