# spring-boot-demo-10-1：WEB 应用开发 - Servlets、Filters、Listeners

> 工程目录：[`spring-boot-demo-10-1`](../../spring-boot-demo-10-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **WEB 应用开发 - Servlets、Filters、Listeners** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- **`@ServletComponentScan`**：在 Spring Boot 中启用对 `@WebServlet`、`@WebFilter`、`@WebListener` 的扫描注册，替代传统 `web.xml` 声明三大组件。
- **无 web.xml 注册 Servlet 组件**：Boot 主推 Java 配置与注解；Filter/Servlet 仍走 Servlet 规范生命周期，与 Spring MVC 的 DispatcherServlet 并存。
- **Filter 执行时机**：在请求进入 DispatcherServlet 之前（及响应返回前）执行链式过滤，常用于编码、鉴权、日志；本模块用 Filter 打日志演示顺序。

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


## 关键代码说明

Servlet 3.0 的 `@WebServlet`、`@WebFilter`、`@WebListener` 不会自动生效。启动类上的 `@ServletComponentScan` 才会扫描并注册它们。

```java
@ServletComponentScan
@SpringBootApplication
public class SpringBootDemo101Application {
```

### Servlet

```java
@WebServlet(urlPatterns = "/roncoo", name = "customServlet")
public class CustomServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().write("hello world");
    }
}
```

`urlPatterns="/roncoo"` 注册独立地址，和 `@RequestMapping` 无关。访问 `/roncoo` 不经过 `WebController`，响应体是 `hello world`。

### Filter

```java
@WebFilter(urlPatterns = "/*")
public class CustomFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("do filter");
        chain.doFilter(request, response);
    }
}
```

`/*` 表示所有请求都会进来。`chain.doFilter` 必须调用，否则请求停在过滤器里，后面的 Servlet 和 Controller 都不会执行。`init` 在启动时打印 `init filter`，`destroy` 在容器关闭时打印。

### Listener

```java
@WebListener
public class CustomListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("contextInitialized");
    }
}
```

应用启动、ServletContext 创建完成时打印 `contextInitialized`，早于具体请求。

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
