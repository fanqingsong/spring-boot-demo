# spring-boot-demo-05-1：日志配置 - Logback

> 工程目录：[`spring-boot-demo-05-1`](../../spring-boot-demo-05-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **日志配置 - Logback** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- `logging.config=classpath:logback-roncoo.xml` 指定 Logback 外部 XML
- 按 profile 区分日志级别与滚动文件 Appender
- SLF4J 门面 + Logback 实现（Spring Boot 默认）

## 代码实战（对照源码）

### 1. `logback-roncoo.xml`

Console + 按环境 RollingFile；logger 包级别控制。

### 2. `application.properties`

通过 `logging.config` 指向自定义 logback 文件。

## 运行与验证

- 启动后观察控制台与 logs 目录（若配置）下是否生成日志文件。

## 动手练习

- 将某个包的日志级别改为 DEBUG，触发 Controller 请求看输出。


## 关键源码路径

按下面路径在 IDE 中打开对照（相对各 demo 工程根目录）：

**Java**

- `src/main/java/com/roncoo/education/SpringBootDemo51Application.java`
- `src/main/java/com/roncoo/education/bean/User.java`
- `src/main/java/com/roncoo/education/controller/IndexController.java`

**配置/模板**

- `src/main/resources/application-dev.properties`
- `src/main/resources/application-prod.properties`
- `src/main/resources/application-test.properties`
- `src/main/resources/application.properties`
- `src/main/resources/logback-roncoo.xml`
## 学习路径

- 上一模块：[`spring-boot-demo-04-2`](./spring-boot-demo-04-2.md)
- 下一模块：[`spring-boot-demo-05-2`](./spring-boot-demo-05-2.md)
