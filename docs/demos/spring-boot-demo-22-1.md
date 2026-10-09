# spring-boot-demo-22-1：使用异步消息服务 - AMQP（RabbitMQ）

> 工程目录：[`spring-boot-demo-22-1`](../../spring-boot-demo-22-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **使用异步消息服务 - AMQP（RabbitMQ）** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

本节每个知识点从 **为何出现**、**解决什么问题**、**若没有会怎样** 三方面说明，便于理解「为什么要学这一项」，而不只是记名词。

- **`spring-boot-starter-amqp`**
  - **为何出现**：JMS 点对点简单，但路由需求复杂（广播、主题、延迟）时 AMQP 模型更灵活；RabbitMQ 是主流实现。
  - **解决什么问题**：集成 RabbitMQ，支持多种 Exchange 类型与可靠投递。
  - **若没有会怎样**：只用内存 Queue 无法持久化、无法跨进程，进程挂掉消息全丢。

- **Exchange、Queue、RoutingKey**
  - **为何出现**：生产者不应直接绑死消费者队列名，否则扩展新消费者要改生产者。
  - **解决什么问题**：Exchange 按规则路由到 Queue，RoutingKey 决定绑定，新增消费者只加绑定。
  - **若没有会怎样**：单队列硬编码， fan-out、优先级、死信等场景无法实现，系统耦合死。

- **配置类 + Component**
  - **为何出现**：队列/交换器/绑定是基础设施，应与业务发送/监听代码分离。
  - **解决什么问题**：Configuration 声明拓扑；Component + `@RabbitListener` 负责业务，结构清晰。
  - **若没有会怎样**：发送端临时 declare 队列，拓扑散落代码里，环境不一致时消费不到消息。

## 代码实战（对照源码）

### 1. 配置类

声明队列、交换器、绑定。

### 2. 组件

发送与监听 RabbitMQ 消息。

## 运行与验证

- RabbitMQ 管理界面查看队列；发消息验证消费。

## 动手练习

- 实现简单延迟队列或死信（进阶）。


## 关键代码说明

结构与 21-1 的 JMS 平行：一个配置类声明队列，一个组件负责发和收。启动类使用 `@EnableRabbit`。

```java
@Configuration
public class AmqpConfiguration {
    @Bean
    public Queue queue() {
        return new Queue("roncoo.queue");
    }
}
```

```java
@Component
public class RoncooAmqpComponent {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(String msg) {
        this.amqpTemplate.convertAndSend("roncoo.queue", msg);
    }

    @RabbitListener(queues = "roncoo.queue")
    public void receiveQueue(String text) {
        System.out.println("接受到：" + text);
    }
}
```

- `new Queue("roncoo.queue")` 是 RabbitMQ 的队列声明，应用启动时会在 broker 上确保该队列存在。
- `convertAndSend` 的第一个参数是队列名，消息体是字符串。
- `@RabbitListener(queues = "roncoo.queue")` 订阅同一队列。消费者方法的参数类型是 `String`，框架按消息转换器把 body 转成文本。
- 连接主机、端口、账号在 spring rabbit 配置里。本机没有 RabbitMQ 时，监听容器会在启动阶段报连接失败。

## 关键源码路径

按下面路径在 IDE 中打开对照（相对各 demo 工程根目录）：

**Java**

- `src/main/java/com/roncoo/example/SpringBootDemo221Application.java`
- `src/main/java/com/roncoo/example/bean/RoncooUser.java`
- `src/main/java/com/roncoo/example/bean/RoncooUserLog.java`
- `src/main/java/com/roncoo/example/cache/RoncooUserLogCache.java`
- `src/main/java/com/roncoo/example/cache/impl/RoncooUserLogCacheImpl.java`
- `src/main/java/com/roncoo/example/component/RoncooAmqpComponent.java`
- `src/main/java/com/roncoo/example/component/RoncooMongodbComponent.java`
- `src/main/java/com/roncoo/example/component/RoncooRedisComponent.java`
- `src/main/java/com/roncoo/example/controller/ApiController.java`
- `src/main/java/com/roncoo/example/controller/FileController.java`
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
- `src/main/java/com/roncoo/example/util/configuration/AmqpConfiguration.java`
- `src/main/java/com/roncoo/example/util/configuration/CustomCorsConfiguration.java`
- `src/main/java/com/roncoo/example/util/configuration/CustomCorsConfiguration2.java`
- `src/main/java/com/roncoo/example/util/configuration/RedisCacheConfiguration.java`
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
## 学习路径

- 上一模块：[`spring-boot-demo-21-1`](./spring-boot-demo-21-1.md)
- 下一模块：[`spring-boot-demo-23-1`](./spring-boot-demo-23-1.md)
