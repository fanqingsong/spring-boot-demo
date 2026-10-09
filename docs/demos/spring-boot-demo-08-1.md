# spring-boot-demo-08-1：WEB 应用开发 - 模板引擎 JSP

> 工程目录：[`spring-boot-demo-08-1`](../../spring-boot-demo-08-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **WEB 应用开发 - 模板引擎 JSP** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- **`tomcat-embed-jasper` + `jstl`**：内嵌 Tomcat 默认不编译 JSP，需额外依赖 Jasper 引擎；JSTL 提供标签库，在 JSP 里简化循环、条件输出。
- **`src/main/webapp`**：JSP 传统目录结构，与 Boot 默认的 `templates/` 不同；视图文件放在 webapp 下由 Jasper 处理。
- **`ServletInitializer`**：继承 `SpringBootServletInitializer` 并提供 `configure` 方法，使应用可打成 **war** 部署到外置 Tomcat，而不仅是可执行 jar。

## 代码实战（对照源码）

### 1. `pom.xml`

jsp 相关依赖与 packaging 配置。

### 2. `ServletInitializer`

继承 `SpringBootServletInitializer`，外置容器入口。

## 运行与验证

- jar 方式运行访问 JSP 页面；理解内嵌 Jasper 的限制。

## 动手练习

- 对比为何 Spring Boot 官方更推荐 Freemarker/Thymeleaf 而非 JSP。


## 关键代码说明

JSP 不能像 FreeMarker 那样只放在 `classpath:/templates`。本 demo 把页面放在 `src/main/webapp`，并改成 war。

### pom：war + Jasper

```xml
<packaging>war</packaging>
```

另外引入 `tomcat-embed-jasper`（编译 JSP）和 `jstl`，scope 为 `provided`，因为运行时由外置或内嵌 Tomcat 提供。

### 视图前后缀

```properties
spring.mvc.view.prefix=/WEB-INF/templates/
spring.mvc.view.suffix=.jsp
```

Controller 仍 `return "index"`。解析结果是 `/WEB-INF/templates/index.jsp`，对应文件 `src/main/webapp/WEB-INF/templates/index.jsp`。

### JSP 页面

```jsp
<h1 id="title">${title}</h1>
<c:url value="http://www.roncoo.com" var="url"/>
<spring:url value="http://www.roncoo.com" htmlEscape="true" var="springUrl" />
```

`${title}` 是 JSP EL，读的是 `ModelMap` 里的同名属性。`c:url`、`spring:url` 需要文件顶部的 taglib。静态资源在 `src/main/webapp/static/`，链接写成 `/static/css/...`，和 jar 工程里 `classpath:/static` 直接映射到 `/css` 不一样。

### 外置容器入口

```java
public class ServletInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringBootDemo81Application.class);
    }
}
```

打成 war 丢进外部 Tomcat 时，容器不会调用 `main`，而是通过这个类启动 Spring Boot。`main` 仍保留，便于 `mvn spring-boot:run`。

## 关键源码路径

按下面路径在 IDE 中打开对照（相对各 demo 工程根目录）：

**Java**

- `src/main/java/com/roncoo/example/ServletInitializer.java`
- `src/main/java/com/roncoo/example/SpringBootDemo81Application.java`
- `src/main/java/com/roncoo/example/controller/WebController.java`

**配置/模板**

- `src/main/resources/application-dev.properties`
- `src/main/resources/application-prod.properties`
- `src/main/resources/application-test.properties`
- `src/main/resources/application.properties`
- `src/main/resources/logback-roncoo.xml`

**Webapp**

- `src/main/webapp/WEB-INF/templates/index.jsp`
## 学习路径

- 上一模块：[`spring-boot-demo-07-1`](./spring-boot-demo-07-1.md)
- 下一模块：[`spring-boot-demo-09-1`](./spring-boot-demo-09-1.md)
