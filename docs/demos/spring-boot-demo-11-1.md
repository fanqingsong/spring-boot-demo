# spring-boot-demo-11-1：WEB 应用开发 - CORS 支持

> 工程目录：[`spring-boot-demo-11-1`](../../spring-boot-demo-11-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **WEB 应用开发 - CORS 支持** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- 跨域：浏览器限制不同源 AJAX
- `WebMvcConfigurerAdapter.addCorsMappings` 全局 CORS
- `@CrossOrigin` 注解在 Controller/方法上
- `CustomCorsConfiguration` / `CustomCorsConfiguration2` 两种配置方式

## 代码实战（对照源码）

### 1. `ApiController`

供前端或 Postman 跨域调用的 API。

### 2. 配置类

允许的来源、方法、Header 等。

## 运行与验证

- 用浏览器控制台或前端静态页跨端口请求 API，验证 CORS 头。

## 动手练习

- 仅对 `/api/**` 开放 CORS，其他路径禁止。


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
