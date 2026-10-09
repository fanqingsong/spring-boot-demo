# spring-boot-demo-07-1：WEB 应用开发 - 模板引擎 Thymeleaf

> 工程目录：[`spring-boot-demo-07-1`](../../spring-boot-demo-07-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **WEB 应用开发 - 模板引擎 Thymeleaf** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

本节每个知识点从 **为何出现**、**解决什么问题**、**若没有会怎样** 三方面说明，便于理解「为什么要学这一项」，而不只是记名词。

- **Thymeleaf 自然模板**
  - **为何出现**：设计师/前端希望模板仍是标准 HTML，能在浏览器直接打开预览，JSP/部分引擎做不到。
  - **解决什么问题**：静态 HTML + `th:*` 属性，协作时前端不依赖后端启动即可看布局。
  - **若没有会怎样**：模板强依赖服务端语法，前后端无法并行，改样式必须联调启动服务。

- **`starter-thymeleaf`**
  - **为何出现**：与 FreeMarker 一样，需要 Boot 自动配置 TemplateEngine 与 ThymeleafViewResolver。
  - **解决什么问题**：加依赖即能用 `templates/*.html`，与 06-1 结构平行便于对比两种引擎。
  - **若没有会怎样**：手动注册 Thymeleaf Bean，版本与 Spring MVC 集成易出错。

- **视图名 → `templates/*.html`**
  - **为何出现**：统一约定减少配置；Thymeleaf 默认后缀 `.html` 与 FreeMarker 的 `.ftl` 区分。
  - **解决什么问题**：Controller 仍返回 index 视图名，但解析到 HTML 模板，学习 06-1 后可快速切换引擎。
  - **若没有会怎样**：返回视图名却放 `.ftl` 或路径错误，页面 500 或模板找不到。

## 代码实战（对照源码）

### 1. Controller 与模板

结构与 06-1 类似，模板改为 Thymeleaf 语法。

## 运行与验证

- 访问 `/web/index` 对比 FreeMarker 与 Thymeleaf 项目模板语法差异。

## 动手练习

- 使用 `th:text` 输出转义内容，理解 XSS 防护。


## 关键代码说明

Controller 与 06-1 相同：`@Controller` 返回视图名 `"index"`，`ModelMap` 放入 `title`。变的是模板引擎和语法。

Thymeleaf 默认模板目录仍是 `classpath:/templates/`，后缀是 `.html`，所以返回值 `"index"` 对应 `templates/index.html`。

页面上取值的写法从 FreeMarker 的 `${title}` 换成 Thymeleaf 属性：

```html
<h1 id="title" th:text="${title}"></h1>
```

`th:text` 在服务端把元素的文本替换成模型里的 `title`。用浏览器查看源码时，看到的是已经替换后的 `hello world`，而不是 `th:text`。静态资源路径 `/css`、`/webjars` 的规则与 06-1 相同。

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
