# spring-boot-demo-04-1：配置文件 - 多环境配置（Properties）

> 工程目录：[`spring-boot-demo-04-1`](../../spring-boot-demo-04-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **配置文件 - 多环境配置（Properties）** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- `spring.profiles.active` 指定当前环境（dev/test/prod）
- 主配置 + `application-{profile}.properties` 覆盖同名键
- Jackson 日期格式可在主配置统一声明

## 代码实战（对照源码）

### 1. 主 `application.properties`

设置 `spring.profiles.active=dev` 及 Jackson 格式。

### 2. `application-dev.properties` 等

各环境 `server.port`、日志路径等差异化配置。

## 运行与验证

- 分别设置 active 为 dev/test/prod，观察启动日志中的端口。

## 动手练习

- 新增 `application-local.properties` 并在本地使用。


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
