# spring-boot-demo-23-1：调用 REST 服务 - 如何使用代理

> 工程目录：[`spring-boot-demo-23-1`](../../spring-boot-demo-23-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **调用 REST 服务 - 如何使用代理** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- **HttpClient / RestTemplate**：在服务端作为 HTTP **客户端** 调用外部 REST API（与 02-1 提供 API 相反），支持 GET/POST、JSON  body 等。
- **`RestRoncooController`**：演示接收 JSON、解析 `JsonNode`、再触发本地缓存/DAO 更新，模拟 webhook 或第三方回调集成。
- **与 02-1 的关系**：02-1 是「对外暴露接口」；本模块是「调用别人接口」，组成完整微服务/集成场景的两半。

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
