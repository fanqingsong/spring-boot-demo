# spring-boot-demo-07-1：WEB 应用开发 - 模板引擎 Thymeleaf

> 工程目录：[`spring-boot-demo-07-1`](../../spring-boot-demo-07-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **WEB 应用开发 - 模板引擎 Thymeleaf** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- Thymeleaf 自然模板，可静态预览
- starter-thymeleaf 自动配置
- 视图名对应 `templates/*.html`

## 代码实战（对照源码）

### 1. Controller 与模板

结构与 06-1 类似，模板改为 Thymeleaf 语法。

## 运行与验证

- 访问 `/web/index` 对比 FreeMarker 与 Thymeleaf 项目模板语法差异。

## 动手练习

- 使用 `th:text` 输出转义内容，理解 XSS 防护。


## 关键源码路径

按下面路径在 IDE 中打开对照（相对各 demo 工程根目录）：

**Java**

- `src/main/java/com/roncoo/example/SpringBootDemo71Application.java`
- `src/main/java/com/roncoo/example/controller/WebController.java`

**配置/模板**

- `src/main/resources/application-dev.properties`
- `src/main/resources/application-prod.properties`
- `src/main/resources/application-test.properties`
- `src/main/resources/application.properties`
- `src/main/resources/logback-roncoo.xml`
- `src/main/resources/templates/index.html`
## 学习路径

- 上一模块：[`spring-boot-demo-06-1`](./spring-boot-demo-06-1.md)
- 下一模块：[`spring-boot-demo-08-1`](./spring-boot-demo-08-1.md)
