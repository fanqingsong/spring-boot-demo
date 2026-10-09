# spring-boot-demo-08-1：WEB 应用开发 - 模板引擎 JSP

> 工程目录：[`spring-boot-demo-08-1`](../../spring-boot-demo-08-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **WEB 应用开发 - 模板引擎 JSP** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- JSP 需 `tomcat-embed-jasper` + `jstl`
- 视图位于 `src/main/webapp`（传统结构）
- `ServletInitializer` 支持打成 war 部署到外置 Tomcat

## 代码实战（对照源码）

### 1. `pom.xml`

jsp 相关依赖与 packaging 配置。

### 2. `ServletInitializer`

继承 `SpringBootServletInitializer`，外置容器入口。

## 运行与验证

- jar 方式运行访问 JSP 页面；理解内嵌 Jasper 的限制。

## 动手练习

- 对比为何 Spring Boot 官方更推荐 Freemarker/Thymeleaf 而非 JSP。


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
