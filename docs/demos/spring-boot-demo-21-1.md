# spring-boot-demo-21-1：使用异步消息服务 - JMS（ActiveMQ）

> 工程目录：[`spring-boot-demo-21-1`](../../spring-boot-demo-21-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **使用异步消息服务 - JMS（ActiveMQ）** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- **`spring-boot-starter-activemq`**：集成 ActiveMQ 作为 JMS 消息中间件，实现生产者与消费者解耦、异步处理。
- **`JmsTemplate` / `@JmsListener`**：Template 在代码里发送消息到队列/主题；Listener 标注方法自动消费指定目的地消息。
- **异步解耦场景**：注册、下单后主流程只发消息，邮件/统计等由消费者稍后处理，提高接口响应速度并削峰填谷。

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
