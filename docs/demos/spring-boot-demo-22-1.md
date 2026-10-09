# spring-boot-demo-22-1：使用异步消息服务 - AMQP（RabbitMQ）

> 工程目录：[`spring-boot-demo-22-1`](../../spring-boot-demo-22-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **使用异步消息服务 - AMQP（RabbitMQ）** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- **`spring-boot-starter-amqp`**：集成 RabbitMQ（AMQP 协议），支持更灵活的路由模型 than 简单 JMS 队列。
- **Exchange、Queue、RoutingKey**：消息先发到交换器，再按绑定规则路由到队列；RoutingKey 决定消息进哪个队列，便于多消费者分流。
- **配置类 + Component**：配置类声明队列、交换器、绑定关系；Component 负责发送与 `@RabbitListener` 消费，与 21-1 JMS 模式对照学习。

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
