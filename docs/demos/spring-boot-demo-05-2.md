# spring-boot-demo-05-2：日志配置 - Log4j2

> 工程目录：[`spring-boot-demo-05-2`](../../spring-boot-demo-05-2) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **日志配置 - Log4j2** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- 排除 `spring-boot-starter-logging`，引入 `spring-boot-starter-log4j2`
- 同一应用只能选一种日志实现
- `logging.config` 指向 `log4j2-*.xml`

## 代码实战（对照源码）

### 1. `pom.xml`

排除默认 logging，添加 log4j2 starter。

### 2. `log4j2-dev.xml` / `log4j2-prod.xml`

按环境配置文件路径在 `application-*.properties` 中指定。

### 3. Controller 中 Logger

与 05-1 相同 API（SLF4J），底层换为 Log4j2。

## 运行与验证

- 对比 05-1 与 05-2 的 pom 差异；运行 05-2 确认无 Logback 冲突报错。

## 动手练习

- 在 log4j2 中增加异步 Appender 配置（查官方文档）。


## 关键源码路径

按下面路径在 IDE 中打开对照（相对各 demo 工程根目录）：

**Java**

- `src/main/java/com/roncoo/education/SpringBootDemo52Application.java`
- `src/main/java/com/roncoo/education/bean/User.java`
- `src/main/java/com/roncoo/education/controller/IndexController.java`

**配置/模板**

- `src/main/resources/application-dev.properties`
- `src/main/resources/application-prod.properties`
- `src/main/resources/application-test.properties`
- `src/main/resources/application.properties`
- `src/main/resources/log4j2-dev.xml`
- `src/main/resources/log4j2-prod.xml`
- `src/main/resources/log4j2-test.xml`
## 学习路径

- 上一模块：[`spring-boot-demo-05-1`](./spring-boot-demo-05-1.md)
- 下一模块：[`spring-boot-demo-06-1`](./spring-boot-demo-06-1.md)
