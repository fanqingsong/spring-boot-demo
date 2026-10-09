# spring-boot-demo-11-1：WEB 应用开发 - CORS 支持

> 工程目录：[`spring-boot-demo-11-1`](../../spring-boot-demo-11-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **WEB 应用开发 - CORS 支持** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- **浏览器同源策略与跨域**：前端页面与 API 不同源（协议/域名/端口任一不同）时，浏览器默认禁止 JS 读跨域响应，需服务端返回 CORS 头放行。
- **`WebMvcConfigurerAdapter.addCorsMappings`**：在 Java 配置里全局定义允许的来源、方法、Header，一次配置对所有匹配的 URL 生效。
- **`@CrossOrigin`**：标注在 Controller 类或方法上，细粒度开放单个接口的跨域，适合与全局配置组合或覆盖。
- **两种配置类示例**：`CustomCorsConfiguration` 与 `CustomCorsConfiguration2` 演示不同写法（实现接口 vs `@Bean`），效果都是向响应添加 `Access-Control-*` 头。

## 代码实战（对照源码）

### 1. `ApiController`

供前端或 Postman 跨域调用的 API。

### 2. 配置类

允许的来源、方法、Header 等。

## 运行与验证

- 用浏览器控制台或前端静态页跨端口请求 API，验证 CORS 头。

## 动手练习

- 仅对 `/api/**` 开放 CORS，其他路径禁止。


## 关键代码说明

浏览器跨域时会先看响应头 `Access-Control-Allow-Origin`。本 demo 演示了三种加这个头的方式，其中全局两种目前被注释，真正生效的是方法上的 `@CrossOrigin`。

### 只对一个接口放开

```java
@RestController
@RequestMapping("/api")
public class ApiController {

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public HashMap<String, Object> get(@RequestParam String name) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("title", "hello world");
        map.put("name", name);
        return map;
    }
}
```

`origins` 限定允许的来源。其它来源的浏览器脚本调用 `/api/get` 会被浏览器拦截。这个注解只作用于 `get` 方法。

### 全局方式（源码里已注释，作为对照）

`CustomCorsConfiguration` 注册一个 `WebMvcConfigurer`，在 `addCorsMappings` 里配置：

```java
registry.addMapping("/api/**").allowedOrigins("http://localhost:8080");
```

`CustomCorsConfiguration2` 直接继承 `WebMvcConfigurerAdapter` 并覆盖同一方法，效果等价。两处调用都处于注释状态，避免和 `@CrossOrigin` 叠在一起。需要全局 CORS 时，取消其中一处注释即可，不必两种都打开。

## 关键源码路径

按下面路径在 IDE 中打开对照（相对各 demo 工程根目录）：

**Java**

- `src/main/java/com/roncoo/example/SpringBootDemo111Application.java`
- `src/main/java/com/roncoo/example/controller/ApiController.java`
- `src/main/java/com/roncoo/example/controller/WebController.java`
- `src/main/java/com/roncoo/example/handler/BizExcepiton.java`
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
- `src/main/resources/logback-roncoo.xml`
- `src/main/resources/public/error/404.html`
- `src/main/resources/templates/error/500.ftl`
- `src/main/resources/templates/error/5xx.ftl`
- `src/main/resources/templates/error/error.ftl`
- `src/main/resources/templates/index.ftl`
## 学习路径

- 上一模块：[`spring-boot-demo-10-1`](./spring-boot-demo-10-1.md)
- 下一模块：[`spring-boot-demo-12-1`](./spring-boot-demo-12-1.md)
