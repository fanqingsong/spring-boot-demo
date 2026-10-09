# spring-boot-demo-05-1：日志配置 - Logback

> 工程目录：[`spring-boot-demo-05-1`](../../spring-boot-demo-05-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **日志配置 - Logback** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- **`logging.config=classpath:logback-roncoo.xml`**：告诉 Spring Boot 不要只用默认 logback 规则，而加载你指定的 XML，从而自定义控制台、文件、滚动策略和包级别。
- **按 profile 区分日志**：在 logback XML 里用 `springProfile` 等条件为 dev/prod 配不同级别或 Appender，开发可 DEBUG、生产可 WARN 并写滚动文件，互不影响。
- **SLF4J + Logback**：业务代码只依赖 SLF4J 接口（`LoggerFactory`），具体写日志由 Logback 实现；这是 Spring Boot 默认组合，换实现时需像 05-2 那样排除默认绑定。

## 代码实战（对照源码）

### 1. `logback-roncoo.xml`

Console + 按环境 RollingFile；logger 包级别控制。

### 2. `application.properties`

通过 `logging.config` 指向自定义 logback 文件。

## 运行与验证

- 启动后观察控制台与 logs 目录（若配置）下是否生成日志文件。

## 动手练习

- 将某个包的日志级别改为 DEBUG，触发 Controller 请求看输出。


## 关键代码说明

### 指定使用哪份 Logback 配置

```properties
logging.config=classpath:logback-roncoo.xml
spring.profiles.active=dev
```

不写 `logging.config` 时 Spring Boot 用默认的 `logback-spring.xml` / 内置配置。这里显式指向自定义文件，并且文件里用 `<springProfile>` 按环境分叉。

### `logback-roncoo.xml` 按 profile 选择输出

开发环境只打控制台，并把业务包调到 debug：

```xml
<springProfile name="dev">
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>${PATTERN}</pattern>
        </encoder>
    </appender>
    <logger name="com.roncoo.education" level="debug"/>
    <root level="info">
        <appender-ref ref="CONSOLE" />
    </root>
</springProfile>
```

- `<logger name="com.roncoo.education" level="debug"/>` 只放宽这个包，其它包仍受 root 的 info 限制。
- `<springProfile name="test">` 换成按天滚动的文件 Appender，root 为 info。
- `<springProfile name="prod">` 同样写文件，但 root 是 warn，debug/info 不会落盘。

`<springProfile>` 读的是 Spring 的 `spring.profiles.active`，所以换环境不用改 XML 里的 if。

### 业务代码只依赖 SLF4J

```java
private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

@RequestMapping
public String index() {
    logger.debug("this is a log test, debug");
    logger.info("this is a log test, info");
    return "hello world";
}
```

`Logger` / `LoggerFactory` 来自 `org.slf4j`，类里没有 Logback 的类型。dev 下访问 `/index` 时，debug 和 info 都会出现在控制台；切到 prod 后这两条都低于 warn，文件里看不到它们。

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
