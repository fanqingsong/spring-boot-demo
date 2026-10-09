# spring-boot-demo-23-1：调用 REST 服务 - 如何使用代理

> 工程目录：[`spring-boot-demo-23-1`](../../spring-boot-demo-23-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **调用 REST 服务 - 如何使用代理** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

本节每个知识点从 **为何出现**、**解决什么问题**、**若没有会怎样** 三方面说明，便于理解「为什么要学这一项」，而不只是记名词。

- **HttpClient / RestTemplate**
  - **为何出现**：微服务与第三方集成里，服务端也要当 HTTP 客户端调支付、短信、内部 API。
  - **解决什么问题**：RestTemplate/HttpClient 封装连接、序列化，比裸 URLConnection 可靠。
  - **若没有会怎样**：用错误工具或手写 socket，超时、编码、HTTPS 证书问题频发。

- **`RestRoncooController`**
  - **为何出现**：需要示例：收 JSON 回调 + 调本地缓存/DAO，模拟真实集成边界。
  - **解决什么问题**：演示 `@RequestBody JsonNode` 与内部 Service 协作，类似 webhook 处理。
  - **若没有会怎样**：只会写对外 API，不会消费回调，集成任务无法独立完成。

- **与 02-1 的关系**
  - **为何出现**：完整系统既要有 Server 也要有 Client，教学应成对出现。
  - **解决什么问题**：02 提供 REST；23 消费 REST，形成闭环理解 HTTP 双向角色。
  - **若没有会怎样**：以为 Spring 只做网站后端，遇到「调别人接口」不知如何下手。

## 代码实战（对照源码）

### 1. `RestRoncooController`

`@RequestBody JsonNode` 解析 JSON；结合 `RoncooUserLogCache` 更新数据。

### 2. httpclient 依赖

配置代理或连接池（视项目配置）。

## 运行与验证

- curl 模拟调用 `/rest/update` 等接口，观察日志。

## 动手练习

- 封装统一 RestTemplate Bean，设置超时与错误处理。


## 关键代码说明

本讲是**调用方**。服务端是 `RestRoncooController`，客户端在测试类里用 `RestTemplateBuilder`。

### 被调用的接口

```java
@RestController
@RequestMapping(value = "/rest", method = RequestMethod.POST)
public class RestRoncooController {

    @RequestMapping(value = "/update")
    public RoncooUserLog update(@RequestBody JsonNode jsonNode) {
        RoncooUserLog bean = RoncooUserLogCache.selectById(jsonNode.get("id").asInt(1));
        // 改字段后 updateById，返回 bean
        return bean;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public RoncooUserLog update2(@PathVariable(value = "id") Integer id) {
        // 同样按 id 查出并更新
    }
}
```

类级别默认 POST。`update` 的 body 是 JSON，用 `JsonNode` 取 `id`。`update2` 在方法上改成 GET，id 来自路径。

### 测试里发起调用

```java
RoncooUserLog bean = restTemplateBuilder.build()
    .getForObject("http://localhost:8080/rest/update/{id}", RoncooUserLog.class, 1);

Map<String, Object> map = new HashMap<String, Object>();
map.put("id", 2);
bean = restTemplateBuilder.build()
    .postForObject("http://localhost:8080/rest/update", map, RoncooUserLog.class);
```

`{id}` 会被最后一个参数 `1` 替换。`postForObject` 把 Map 序列化成 JSON。第三个参数 `RoncooUserLog.class` 表示把响应 JSON 反序列化成该类型。

### 走代理

```java
static class ProxyCustomizer implements RestTemplateCustomizer {
    public void customize(RestTemplate restTemplate) {
        HttpHost proxy = new HttpHost(proxyHost, proxyPort);
        HttpClient httpClient = HttpClientBuilder.create()
            .setRoutePlanner(new DefaultProxyRoutePlanner(proxy) { /* ... */ })
            .build();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
    }
}
```

`additionalCustomizers(new ProxyCustomizer())` 只影响这一次 `build()` 出来的 `RestTemplate`。请求会先发到 `proxyHost:proxyPort`。源码里的地址是示例，运行前要换成你自己的代理；直连本机接口时不要加这个 customizer。

## 关键源码路径

按下面路径在 IDE 中打开对照（相对各 demo 工程根目录）：

**Java**

- `src/main/java/com/roncoo/example/SpringBootDemo231Application.java`
- `src/main/java/com/roncoo/example/bean/RoncooUser.java`
- `src/main/java/com/roncoo/example/bean/RoncooUserLog.java`
- `src/main/java/com/roncoo/example/cache/RoncooUserLogCache.java`
- `src/main/java/com/roncoo/example/cache/impl/RoncooUserLogCacheImpl.java`
- `src/main/java/com/roncoo/example/component/RoncooMongodbComponent.java`
- `src/main/java/com/roncoo/example/component/RoncooRedisComponent.java`
- `src/main/java/com/roncoo/example/controller/ApiController.java`
- `src/main/java/com/roncoo/example/controller/FileController.java`
- `src/main/java/com/roncoo/example/controller/RestRoncooController.java`
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
- `src/main/java/com/roncoo/example/util/filter/CustomFilter.java`
- `src/main/java/com/roncoo/example/util/listerner/CustomListener.java`
- `src/main/java/com/roncoo/example/util/servlet/CustomServlet.java`

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

- 上一模块：[`spring-boot-demo-22-1`](./spring-boot-demo-22-1.md)
- 下一模块：[`spring-boot-demo-24-1`](./spring-boot-demo-24-1.md)
