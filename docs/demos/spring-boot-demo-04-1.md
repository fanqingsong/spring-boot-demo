# spring-boot-demo-04-1：配置文件 - 多环境配置（Properties）

> 工程目录：[`spring-boot-demo-04-1`](../../spring-boot-demo-04-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **配置文件 - 多环境配置（Properties）** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

本节每个知识点从 **为何出现**、**解决什么问题**、**若没有会怎样** 三方面说明，便于理解「为什么要学这一项」，而不只是记名词。

- **`spring.profiles.active`**
  - **为何出现**：开发、测试、生产的数据库、端口、密钥不同，全写进一份配置易误用生产库或泄露密码。
  - **解决什么问题**：用 profile 名切换环境专用配置文件，同一份代码多环境运行。
  - **若没有会怎样**：为上线改配置文件再打包，或 dev 连错生产库，造成数据事故或泄露。

- **主配置 + `application-{profile}.properties`**
  - **为何出现**：公共项（Jackson、应用名）各环境相同，差异项（port、jdbc）应隔离，否则重复维护。
  - **解决什么问题**：主文件放公共 + 默认 active；profile 文件只放差异，合并后得到完整 Environment。
  - **若没有会怎样**：一个巨型 properties 里 if/注释区分环境，极易删错行；或 prod 密码被 commit 到仓库。

- **Jackson 日期格式（主配置）**
  - **为何出现**：各环境 API 契约应一致，日期格式不应因 profile 不同而变。
  - **解决什么问题**：在主配置声明一次，所有 profile 共享，环境只改基础设施参数。
  - **若没有会怎样**：每个 profile 重复 jackson 配置，漏配某一环境导致测试通过、生产 JSON 格式不同。

## 代码实战（对照源码）

### 1. 主 `application.properties`

设置 `spring.profiles.active=dev` 及 Jackson 格式。

### 2. `application-dev.properties` 等

各环境 `server.port`、日志路径等差异化配置。

## 运行与验证

- 分别设置 active 为 dev/test/prod，观察启动日志中的端口。

## 动手练习

- 新增 `application-local.properties` 并在本地使用。


## 关键代码说明

多环境的开关在主配置，差异写在 `application-{profile}.properties`。

### 主文件只负责「激活哪个环境」和公共项

```properties
spring.profiles.active=dev

spring.jackson.date-format=yyyy-MM-dd HH:mm:ss
spring.jackson.time-zone=Asia/Chongqing
```

`spring.profiles.active=dev` 让 Spring Boot 再加载 `application-dev.properties`。Jackson 配置没有按环境拆开，三个环境共用。

### 每个环境文件只覆盖会变的项

```properties
# application-dev.properties
server.port=8080

# application-test.properties
server.port=8081

# application-prod.properties
server.port=8082
```

同名键以当前 profile 文件为准。默认启动是 dev，端口 8080。命令行 `--spring.profiles.active=prod` 会覆盖主文件里的 `dev`，于是加载 `application-prod.properties`，端口变成 8082。

本讲的 Controller 与 02-1 相同，不读取 profile。环境差异全部体现在配置文件，改环境不用改 Java。

## 关键源码路径

按下面路径在 IDE 中打开对照（相对各 demo 工程根目录）：

**Java**

- `src/main/java/com/roncoo/education/SpringBootDemo41Application.java`
- `src/main/java/com/roncoo/education/bean/User.java`
- `src/main/java/com/roncoo/education/controller/IndexController.java`

**配置/模板**

- `src/main/resources/application-dev.properties`
- `src/main/resources/application-prod.properties`
- `src/main/resources/application-test.properties`
- `src/main/resources/application.properties`
## 学习路径

- 上一模块：[`spring-boot-demo-03-2`](./spring-boot-demo-03-2.md)
- 下一模块：[`spring-boot-demo-04-2`](./spring-boot-demo-04-2.md)
