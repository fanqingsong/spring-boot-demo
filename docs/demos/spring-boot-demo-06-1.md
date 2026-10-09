# spring-boot-demo-06-1：WEB 应用开发 - 模板引擎 FreeMarker

> 工程目录：[`spring-boot-demo-06-1`](../../spring-boot-demo-06-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **WEB 应用开发 - 模板引擎 FreeMarker** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- `spring-boot-starter-freemarker` 自动配置视图解析器
- `@Controller` + 返回字符串视图名（非 `@RestController`）
- `ModelMap` / `Model` 向模板传参
- 模板位置默认 `classpath:/templates/` 后缀 `.ftl`

## 代码实战（对照源码）

### 1. `WebController`

`@RequestMapping("/web/index")` 返回 `"index"`，对应 `templates/index.ftl`。

### 2. 静态资源

`static/css/` 等由 Spring MVC 默认映射。

## 运行与验证

- 浏览器访问 http://localhost:8080/web/index 查看渲染页面。

## 动手练习

- 在 ftl 中循环输出列表；Controller 传入 `List<String>`。


## 关键源码路径

按下面路径在 IDE 中打开对照（相对各 demo 工程根目录）：

**Java**

- `src/main/java/com/roncoo/example/SpringBootDemo61Application.java`
- `src/main/java/com/roncoo/example/controller/WebController.java`

**配置/模板**

- `src/main/resources/application-dev.properties`
- `src/main/resources/application-prod.properties`
- `src/main/resources/application-test.properties`
- `src/main/resources/application.properties`
- `src/main/resources/logback-roncoo.xml`
- `src/main/resources/templates/index.ftl`
## 学习路径

- 上一模块：[`spring-boot-demo-05-2`](./spring-boot-demo-05-2.md)
- 下一模块：[`spring-boot-demo-07-1`](./spring-boot-demo-07-1.md)
