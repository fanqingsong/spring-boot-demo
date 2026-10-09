# spring-boot-demo-07-1：WEB 应用开发 - 模板引擎 Thymeleaf

> 工程目录：[`spring-boot-demo-07-1`](../../spring-boot-demo-07-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **WEB 应用开发 - 模板引擎 Thymeleaf** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- **Thymeleaf 自然模板**：模板本身是合法 HTML，带 `th:*` 属性；未启动服务时也可在浏览器打开静态预览，利于前后端协作。
- **`starter-thymeleaf`**：与 FreeMarker 类似，自动注册 Thymeleaf 视图解析器，只需加依赖和写 `templates/*.html`。
- **视图名 → `templates/*.html`**：Controller 返回 `"index"` 对应 `templates/index.html`，语法用 `th:text` 等替代 FreeMarker 的 `${}`。

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
