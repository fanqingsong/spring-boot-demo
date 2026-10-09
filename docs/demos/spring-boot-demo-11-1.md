# spring-boot-demo-11-1：WEB 应用开发 - CORS 支持

> 工程目录：[`spring-boot-demo-11-1`](../../spring-boot-demo-11-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **WEB 应用开发 - CORS 支持** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

本节每个知识点从 **为何出现**、**解决什么问题**、**若没有会怎样** 三方面说明，便于理解「为什么要学这一项」，而不只是记名词。

- **浏览器同源策略与跨域**
  - **为何出现**：Web 安全模型禁止页面上的 JS 读取「不同源」接口响应，否则恶意站点可偷用户数据。
  - **解决什么问题**：合法的前后端分离部署（不同 port/域名）需要服务端显式声明 CORS 头，浏览器才放行。
  - **若没有会怎样**：前端 AJAX 报 CORS error，接口其实 200 但 JS 读不到 body，联调误以为后端挂了。

- **`WebMvcConfigurerAdapter.addCorsMappings`**
  - **为何出现**：多个 Controller 都要跨域时，逐个 `@CrossOrigin` 重复且易漏。
  - **解决什么问题**：全局配置 allowedOrigins/Methods/Headers，一次声明覆盖 `/api/**` 等路径。
  - **若没有会怎样**：每个接口 copy 一遍 CORS 配置，新增接口忘记加注解，又出现间歇性跨域失败。

- **`@CrossOrigin`**
  - **为何出现**：个别接口要更严或更松的规则（如只允许某 origin），需要细粒度覆盖全局。
  - **解决什么问题**：在类或方法上声明跨域策略，与全局配置互补或覆盖。
  - **若没有会怎样**：只能全局放开 `*`，安全审计不过；或无法给单个 webhook 接口单独策略。

- **两种配置类示例**
  - **为何出现**：Spring MVC 注册 CORS 有多种写法（继承适配器 vs 注册 CorsFilter Bean），团队需要可对照的范例。
  - **解决什么问题**：`CustomCorsConfiguration` / `CustomCorsConfiguration2` 展示等价目标的不同实现路径。
  - **若没有会怎样**：只抄一种写法不理解原理，升级 Spring 版本后 API 废弃不知如何迁移。

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
