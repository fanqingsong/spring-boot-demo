# spring-boot-demo-05-2：日志配置 - Log4j2

> 工程目录：[`spring-boot-demo-05-2`](../../spring-boot-demo-05-2) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **日志配置 - Log4j2** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。重点掌握：

- 为什么必须排除默认的 `spring-boot-starter-logging`，再引入 `spring-boot-starter-log4j2`
- Log4j2 相对默认 Logback 的主要优势
- 业务代码仍用 SLF4J，只换底层实现

## 核心知识点

- Spring Boot 默认日志实现是 **Logback**（由 `spring-boot-starter-web` 传递引入 `spring-boot-starter-logging`）
- 同一应用的 classpath 上**只能有一种** SLF4J 绑定；Logback 与 Log4j2 同时存在会冲突
- 排除 `spring-boot-starter-logging`，引入 `spring-boot-starter-log4j2`
- `logging.config` 指向 `log4j2-*.xml`
- Controller 仍使用 `LoggerFactory.getLogger`（SLF4J），底层已换成 Log4j2

## 为什么要用 Log4j2

Spring Boot 默认 Logback 已经够用。换 Log4j2 通常是因为下面这些能力，而不是「必须换」。

| 能力 | Logback（默认） | Log4j2 |
| --- | --- | --- |
| 异步日志 | 主要靠 `AsyncAppender` 包一层 | 原生 **Async Logger**（基于 LMAX Disruptor），吞吐更高、延迟更低 |
| 高并发性能 | 够用，同步写文件时易成瓶颈 | 异步场景下通常明显更快，垃圾更少 |
| 关闭级别时的开销 | 常用占位符 `{}` | 同样支持 `{}`，还支持 **lambda 延迟求值**，避免未输出时的字符串拼接 |
| 配置热更新 | 可 scan | `monitorInterval` 即可定时重载 XML，不必重启 |
| 过滤器 / 布局 | 够用 | BurstFilter、RegexFilter、JSON/CSV 等布局更丰富 |
| 可靠性 | 异步队列满可能丢日志 | 关机时尽量刷盘；Appender 失败可配重试/降级 |
| 插件扩展 | 需按 Logback SPI | 注解式 Plugin，扩展 Appender/Layout 更直接 |

展开说明：

1. **异步日志是最大卖点**  
   Log4j2 的 `AsyncLogger` 把日志事件丢进 Disruptor 环形队列，由独立线程写磁盘。业务线程几乎只做入队，高 QPS 下对接口耗时影响更小。动手练习里「增加异步 Appender」就是在体验这一点。

2. **性能与 GC**  
   同步打日志时两者差距不大；一旦打开异步 + 高并发，Log4j2 往往吞吐更高。较新版本还支持 garbage-free 模式，减少临时对象，适合对 GC 敏感的服务。

3. **业务代码更省心**  
   级别关闭时，`logger.debug("user=" + user)` 仍会拼接字符串。Log4j2 API 可用 `logger.debug(() -> "user=" + user)`，只有真正要输出时才计算。本 demo 继续用 SLF4J 的 `{}` 占位符，同样能避免无效拼接。

4. **配置可热加载**  
   生产改日志级别或滚动策略时，给 `<configuration monitorInterval="30">` 即可约 30 秒重载，不必重启进程。

5. **和 Spring Boot 的分工不变**  
   门面仍是 SLF4J，业务代码与 05-1 **完全一样**。换实现只动 `pom.xml` 和 `log4j2-*.xml`，这正是「排除默认 logging + 加 log4j2 starter」的意义。

**什么时候不必换：** 中小项目、日志量不大、团队已熟悉 Logback。Spring Boot 默认 Logback 集成更简单（05-1 甚至不用改 pom）。高吞吐、要原生异步、或公司统一 Log4j2 时再换。

> 安全：Log4j2 曾有 Log4Shell（`JndiLookup`）等高危漏洞。新项目务必使用已修复版本（2.17.1+ / 2.21+ 等当前安全线），并关闭不必要的 Lookup。本仓库 Spring Boot 1.4.x 自带的 Log4j2 **偏旧，仅供学习替换步骤，不要直接用于生产**。

## 代码实战（对照源码）

### 1. `pom.xml`：排除默认 logging，添加 log4j2 starter

`spring-boot-starter-web` 会带上 `spring-boot-starter-logging`（Logback）。若不排除，classpath 上会同时出现 Logback 与 Log4j2 两套 SLF4J 绑定，启动时常见 `LoggerFactory is not a Logback LoggerContext` 或绑定冲突。

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<!-- 使用log4j2 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>
```

`spring-boot-starter-log4j2` 会引入 Log4j2 核心、SLF4J 到 Log4j2 的桥，以及 Spring Boot 对 Log4j2 的适配。排除 + 引入必须成对出现。

### 2. `log4j2-dev.xml` / `log4j2-prod.xml`

按环境配置文件路径在 `application-*.properties` 中指定，例如：

```properties
logging.config=classpath:log4j2-dev.xml
```

`log4j2-dev.xml` 里用 Console Appender，并把 `com.roncoo.education` 设为 debug，便于本地看 `IndexController` 的日志。

### 3. Controller 中 Logger

与 05-1 相同 API（SLF4J），底层换为 Log4j2：

```java
private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

logger.debug("this is a log test, debug");
logger.info("this is a log test, info");
```

## 运行与验证

- 对比 05-1 与 05-2 的 pom 差异：05-1 不用排除 logging；05-2 必须 exclusion + `starter-log4j2`
- 运行 05-2，确认无 Logback 冲突报错
- 访问 `/index`，开发环境下应能看到 debug 与 info 日志

## 动手练习

- 在 log4j2 中增加异步 Appender / AsyncLogger 配置（查官方文档）
- 给 `<configuration>` 加上 `monitorInterval="5"`，改 XML 中的 level，确认无需重启即可生效
- 故意去掉 exclusion 再启动，观察 SLF4J 绑定冲突，加深「为什么必须排除默认 logging」的印象（看完后改回去）

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
