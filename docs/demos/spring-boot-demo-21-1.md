# spring-boot-demo-21-1：使用异步消息服务 - JMS（ActiveMQ）

> 工程目录：[`spring-boot-demo-21-1`](../../spring-boot-demo-21-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **使用异步消息服务 - JMS（ActiveMQ）** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

本节每个知识点从 **为何出现**、**解决什么问题**、**若没有会怎样** 三方面说明，便于理解「为什么要学这一项」，而不只是记名词。

- **`spring-boot-starter-activemq`**
  - **为何出现**：同步调用链路过长时，下游慢或挂掉会拖垮上游；需要异步消息解耦。
  - **解决什么问题**：集成 ActiveMQ（JMS），生产者发消息即返回，消费者异步处理。
  - **若没有会怎样**：注册接口里同步发邮件+写统计，邮件 SMTP 慢导致注册超时、用户体验差。

- **`JmsTemplate` / `@JmsListener`**
  - **为何出现**：JMS API 原生偏繁琐；Spring 提供发送模板与声明式监听。
  - **解决什么问题**：Template 发 Queue/Topic；Listener 方法自动拉消息，专注业务逻辑。
  - **若没有会怎样**：手写 MessageConsumer 循环、ack 管理，易漏 ack、重复消费或丢消息。

- **异步解耦场景**
  - **为何出现**：业务峰值（促销注册）需要削峰，后台慢慢消费。
  - **解决什么问题**：主流程只投递消息，消费者可水平扩展，提高可用性。
  - **若没有会怎样**：峰值全同步处理，线程池打满、DB 连接耗尽，整站 503。

## 代码实战（对照源码）

### 1. `JmsConfiguration`

ConnectionFactory、Listener 容器配置。

### 2. `RoncooJmsComponent`

发送与消费消息示例。

## 运行与验证

- 启动 ActiveMQ；触发业务接口观察队列消息与消费者日志。

## 动手练习

- 定义 Queue 与 Topic 各发一条消息对比。


## 关键代码说明

JMS 需要队列 Bean，以及启动类上的 `@EnableJms`，`@JmsListener` 才会注册监听。

```java
@Configuration
public class JmsConfiguration {
    @Bean
    public Queue queue() {
        return new ActiveMQQueue("roncoo.queue");
    }
}
```

```java
@Component
public class RoncooJmsComponent {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private Queue queue;

    public void send(String msg) {
        this.jmsMessagingTemplate.convertAndSend(this.queue, msg);
    }

    @JmsListener(destination = "roncoo.queue")
    public void receiveQueue(String text) {
        System.out.println("接受到：" + text);
    }
}
```

- `ActiveMQQueue("roncoo.queue")` 声明目的地名称。`send` 把字符串交给 `JmsMessagingTemplate`，由它转成 JMS 消息发到这个队列。
- `@JmsListener(destination = "roncoo.queue")` 的名字必须和队列一致。同一进程里发送后，监听方法会收到文本并打印。
- 目的地是 Queue（点对点），不是 Topic。应用要能连上 `application-*.properties` 里的 ActiveMQ 地址，否则启动或发送时会报连接错误。

## 关键源码路径

按下面路径在 IDE 中打开对照（相对各 demo 工程根目录）：

**Java**

- `src/main/java/com/roncoo/example/SpringBootDemo211Application.java`
- `src/main/java/com/roncoo/example/bean/RoncooUser.java`
- `src/main/java/com/roncoo/example/bean/RoncooUserLog.java`
- `src/main/java/com/roncoo/example/cache/RoncooUserLogCache.java`
- `src/main/java/com/roncoo/example/cache/impl/RoncooUserLogCacheImpl.java`
- `src/main/java/com/roncoo/example/component/RoncooJmsComponent.java`
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
- `src/main/java/com/roncoo/example/util/configuration/CustomCorsConfiguration.java`
- `src/main/java/com/roncoo/example/util/configuration/CustomCorsConfiguration2.java`
- `src/main/java/com/roncoo/example/util/configuration/JmsConfiguration.java`
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

- 上一模块：[`spring-boot-demo-20-1`](./spring-boot-demo-20-1.md)
- 下一模块：[`spring-boot-demo-22-1`](./spring-boot-demo-22-1.md)
