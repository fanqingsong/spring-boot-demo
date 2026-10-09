# spring-boot-demo-10-1：WEB 应用开发 - Servlets、Filters、Listeners

> 工程目录：[`spring-boot-demo-10-1`](../../spring-boot-demo-10-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **WEB 应用开发 - Servlets、Filters、Listeners** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- `@ServletComponentScan` 扫描 `@WebServlet` / `@WebFilter` / `@WebListener`
- 无 web.xml 时注册 Servlet 三大组件的方式
- Filter 在请求到达 DispatcherServlet 前后执行

## 代码实战（对照源码）

### 1. `SpringBootDemo101Application`

类上 `@ServletComponentScan` 开启注解扫描。

### 2. `CustomFilter`

实现 `Filter`，在 chain 前后打日志。

### 3. `CustomServlet`

独立 URL 映射的 HttpServlet。

### 4. `CustomListener`

监听 ServletContext 生命周期。

## 运行与验证

- 启动后分别访问 Servlet 映射路径与 `/web/index`，观察 Filter 日志顺序。

## 动手练习

- 改用 `@Bean` + `FilterRegistrationBean` 注册 Filter（查 Spring Boot 文档）。


## 关键源码路径

按下面路径在 IDE 中打开对照（相对各 demo 工程根目录）：

**Java**

- `src/main/java/com/roncoo/example/SpringBootDemo101Application.java`
- `src/main/java/com/roncoo/example/controller/WebController.java`
- `src/main/java/com/roncoo/example/handler/BizExcepiton.java`
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

- 上一模块：[`spring-boot-demo-09-1`](./spring-boot-demo-09-1.md)
- 下一模块：[`spring-boot-demo-11-1`](./spring-boot-demo-11-1.md)
