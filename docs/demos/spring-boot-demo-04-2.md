# spring-boot-demo-04-2：配置文件 - 多环境配置（YAML）

> 工程目录：[`spring-boot-demo-04-2`](../../spring-boot-demo-04-2) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **配置文件 - 多环境配置（YAML）** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。重点掌握：

- 用 `spring.profiles.active` 决定激活哪个环境
- 在单一 `application.yaml` 里用 `---` 分隔多环境配置
- 部署时通过外部参数覆盖 Profile，而不是改代码
- 生产环境推荐拆分为 `application-{profile}.yml`

## 核心知识点

- **`spring.profiles.active`（核心机制）**：决定当前激活的 Profile；Spring Boot 只合并属于该 profile 的配置段，从而切换端口、数据源等而无需改 Java 代码。
- **YAML 多文档（`---`）**：在一个物理文件里写多段独立文档，每段可绑定不同 profile，适合把 dev/test/prod 收拢到单文件维护（大项目更推荐拆文件，见下文最佳实践）。
- **`spring.profiles: dev`（1.4.x）**：标记该 `---` 段仅在 profile 为 `dev` 时生效；本仓库使用 Spring Boot 1.4.x 写法，2.4+ 需改用 `spring.config.activate.on-profile`。
- **单文件多环境**：减少文件数量、方便对照各环境差异；代价是文件变长，且易误提交敏感配置，生产更常用 04-1 的拆分方式。
- **部署时外部覆盖 active**：通过 `--spring.profiles.active=prod`、`-D` 或环境变量 `SPRING_PROFILES_ACTIVE` 指定环境，同一份 jar 打一次包即可上不同环境，避免为换环境重新编译。

## 代码实战（对照源码）

### 1. `application.yaml`

本 demo 采用**单一 `application.yaml` 多文档写法**：顶部指定默认激活的 profile，后续 `---` 块分别定义 `dev` / `prod` / `test` 的 `server.port`。

```yaml
#配置文件环境配置
spring:
  profiles:
    active: dev

  #jackson
  jackson:
    date-format: yyyy-MM-dd HH:mm:ss
    time-zone: Asia/Chongqing

#端口
server:
  port: 8888

---
spring:
  profiles: dev
server:
  port: 8080

---
spring:
  profiles: prod
server:
  port: 8082

---
spring:
  profiles: test
server:
  port: 8081
```

说明：

- 顶部公共段：`spring.profiles.active: dev`，本地开发默认走开发环境
- 公共配置（如 Jackson 日期格式）写在第一个文档，所有环境共用
- 第一个文档里的 `server.port: 8888` 会被当前激活 profile 段覆盖（dev → 8080，test → 8081，prod → 8082）
- 本 demo 用 `test` 表示测试/预发布；部分团队会命名为 `stage`，含义相同

下面的数据源示例用于说明「各环境配置如何区分」，**不是本 demo 源码里已有的内容**。真实项目里通常还会按环境切换数据库地址、账号等：

```yaml
# Dev 环境配置（示意）
spring:
  profiles: dev
  datasource:
    url: jdbc:mysql://localhost:3306/mydb_dev
    username: root
    password: dev123
server:
  port: 8080

---
# Stage / Test 环境配置（示意）
spring:
  profiles: stage
  datasource:
    url: jdbc:mysql://stage-server:3306/mydb_stage
    username: stage_user
    password: stage123
server:
  port: 8081

---
# Prod 环境配置（示意）
spring:
  profiles: prod
  datasource:
    url: jdbc:mysql://prod-server:3306/mydb_prod
    username: prod_user
    password: prod_strong_pass
server:
  port: 8082
```

> Spring Boot 2.4+ 推荐用 `spring.config.activate.on-profile: prod` 绑定文档，不再使用 `spring.profiles: prod`。本教程工程是 1.4.x，继续用 `spring.profiles` 即可。

## Spring Boot 多环境配置区分方案

### 核心机制：`spring.profiles.active`

Spring Boot 通过 `spring.profiles.active` 属性来决定激活哪个 Profile，从而加载对应的环境配置。本地开发用 `dev`，CI/CD 流水线用 `test`/`stage`，上线部署用 `prod`。

### 部署时切换环境（关键）

这是区分不同环境的核心方式 —— **在部署时通过外部参数覆盖 `spring.profiles.active`，而不是改代码**：

| 部署方式 | 指定 Profile 的命令 |
| --- | --- |
| JAR 包命令行 | `java -jar app.jar --spring.profiles.active=prod` |
| JAR 包 JVM 参数 | `java -Dspring.profiles.active=prod -jar app.jar` |
| Docker 容器 | `docker run -e SPRING_PROFILES_ACTIVE=prod myapp:latest` |
| K8s ConfigMap/Secret | 通过 env 注入 `SPRING_PROFILES_ACTIVE=prod` |
| 配置文件默认值 | 在 `application.yaml` 顶部设置 `spring.profiles.active: prod`（仅作默认，部署仍建议外部覆盖） |

环境变量命名规则：`SPRING_PROFILES_ACTIVE` 对应 `spring.profiles.active`；同理 `SPRING_DATASOURCE_PASSWORD` 对应 `spring.datasource.password`。

### 推荐的最佳实践：拆分配置文件

虽然单一 `application.yaml` 可以写多环境（本 demo 就是这种写法），但生产环境推荐拆分，更清晰、更安全：

```text
src/main/resources/
├── application.yml              # 公共配置（所有环境共用）
├── application-dev.yml          # 开发环境
├── application-stage.yml        # 测试/预发布环境
└── application-prod.yml         # 生产环境
```

`application.yml`（公共配置）：

```yaml
spring:
  profiles:
    active: dev  # 本地开发默认用 dev
```

`application-prod.yml`（生产环境）：

```yaml
server:
  port: 8082

spring:
  datasource:
    url: jdbc:mysql://prod-server:3306/mydb_prod
    username: prod_user
    password: prod_strong_pass
  jpa:
    hibernate:
      ddl-auto: validate  # 生产环境禁止自动建表
    show-sql: false
```

对比本仓库：

- **04-1**：拆分文件写法（`application.properties` + `application-{profile}.properties`）
- **04-2（本讲）**：单一 YAML 多文档写法（`---` 分隔）
- 两者都靠 `spring.profiles.active` 切换；生产项目更推荐 04-1 那种拆分方式

### 各环境配置区分要点

| 配置项 | dev（开发） | stage / test（预发布） | prod（生产） |
| --- | --- | --- | --- |
| `server.port` | 8080 | 8081 | 8082 |
| 数据库地址 | 本地 localhost | 测试服务器 | 生产服务器 |
| `ddl-auto` | `create-drop` | `update` | `validate` |
| `show-sql` | `true`（方便调试） | `true`（可选） | `false` |
| 日志级别 | DEBUG | INFO | WARN 或 INFO |
| 缓存 | 关闭 | 开启（小容量） | 开启（大容量） |
| 密码 | 明文/弱密码 | 环境变量注入 | 密钥管理服务 |

本 demo 目前只演示了端口差异（8080 / 8081 / 8082），上表其余项是真实项目中的常见区分方式。

### 安全提醒

- 敏感信息（密码、密钥）不要硬编码在配置文件中，应通过环境变量或密钥管理服务（如 Vault、K8s Secret）注入
- 环境变量命名规则：`SPRING_DATASOURCE_PASSWORD` 对应 `spring.datasource.password`
- 生产环境务必使用强密码，并定期轮换
- 生产环境 `ddl-auto` 使用 `validate`，禁止自动建表或改表

## 运行与验证

默认 `spring.profiles.active=dev`，启动后监听 **8080**。切换 active 验证端口：

```bash
# 开发环境（默认）→ 8080
mvn spring-boot:run

# 测试/预发布 → 8081
mvn spring-boot:run -Dspring.profiles.active=test

# 生产 → 8082
java -jar target/spring-boot-demo-04-2-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

启动日志中应出现 `The following profiles are active: xxx`，以及对应的 Tomcat 端口。

## 动手练习

- 练习把 04-1 的 properties 多环境改写为本讲 YAML 风格
- 在本 demo 的 YAML 中为 `dev` / `test` / `prod` 增加示意用的数据源或日志级别差异
- 不改 YAML 源码，仅用命令行参数把 Profile 切到 `prod`，确认端口变为 8082
- 尝试把本讲的单一 YAML 再拆成 `application.yml` + `application-dev.yml` 等独立文件

## 总结

区分不同环境的核心就是 `spring.profiles.active` 这一个属性。本地开发用 `dev`，CI/CD 流水线用 `test`/`stage`，上线部署用 `prod`。配置文件可以从单一 `application.yaml` 逐步拆分为 `application-{profile}.yml` 的独立文件，让各环境配置职责清晰、互不干扰。

## 关键代码说明

上文「代码实战」已经贴出完整 YAML。真正决定端口的是下面这段结构：

```yaml
spring:
  profiles:
    active: dev
server:
  port: 8888

---
spring:
  profiles: dev
server:
  port: 8080
```

- 第一个文档是公共配置。`active: dev` 表示默认激活 dev；`server.port: 8888` 是没被 profile 覆盖时的端口。
- `---` 开启下一段文档。`spring.profiles: dev`（1.4.x 写法）表示这段只在 profile 为 `dev` 时合并进来。
- 合并后同名的 `server.port` 被改成 8080。切到 `test`、`prod` 时分别变成 8081、8082。

启动类和 `IndexController` 与 02-1 相同，代码里没有 `if (prod)`。换环境只改 `spring.profiles.active`。

## 关键源码路径

按下面路径在 IDE 中打开对照（相对各 demo 工程根目录）：

**Java**

- `src/main/java/com/roncoo/education/SpringBootDemo42Application.java`
- `src/main/java/com/roncoo/education/bean/User.java`
- `src/main/java/com/roncoo/education/controller/IndexController.java`

**配置/模板**

- `src/main/resources/application.yaml`

## 学习路径

- 上一模块：[`spring-boot-demo-04-1`](./spring-boot-demo-04-1.md)
- 下一模块：[`spring-boot-demo-05-1`](./spring-boot-demo-05-1.md)
