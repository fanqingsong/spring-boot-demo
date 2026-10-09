# spring-boot-demo-06-1：WEB 应用开发 - 模板引擎 FreeMarker

> 工程目录：[`spring-boot-demo-06-1`](../../spring-boot-demo-06-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **WEB 应用开发 - 模板引擎 FreeMarker** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

本节每个知识点从 **为何出现**、**解决什么问题**、**若没有会怎样** 三方面说明，便于理解「为什么要学这一项」，而不只是记名词。

- **`spring-boot-starter-freemarker`**
  - **为何出现**：服务端渲染页面曾是 Web 主流，需要模板引擎把 HTML 与数据合并；Boot 需自动接好 ViewResolver。
  - **解决什么问题**：引入 FreeMarker 并注册视图解析，Controller 返回逻辑视图名即可出 HTML。
  - **若没有会怎样**：手写 ViewResolver Bean、模板路径、编码配置，一个页面 demo 也要大量 XML/Java 配置。

- **`@Controller`（非 RestController）**
  - **为何出现**：管理后台、官网等仍需要 HTML 页面而非纯 JSON，MVC 要区分两种返回语义。
  - **解决什么问题**：返回视图名走视图解析链，配合模板生成 HTML，适合 SSR。
  - **若没有会怎样**：用 RestController 返回字符串 index 视图名时，浏览器收到的是纯文本 index，而不是渲染后的页面。

- **`ModelMap` / `Model`**
  - **为何出现**：模板需要动态数据，不能把 SQL 写在 ftl 里；要在 Controller 与视图间传递对象。
  - **解决什么问题**：键值对进入模型，模板 `${title}` 引用，清晰分离控制器逻辑与展示。
  - **若没有会怎样**：模板只能写死文案，每改文案要改 Java 或改模板两处，无法复用同一模板多数据。

- **模板路径 `classpath:/templates/*.ftl`**
  - **为何出现**：Convention over configuration：若每个项目自定义路径，starter 无法「零配置」运行。
  - **解决什么问题**：返回逻辑视图名 index 即找 templates/index.ftl，与 Boot 其它 demo 路径一致，降低记忆成本。
  - **若没有会怎样**：视图 404、Whitelabel，因为 ViewResolver 找不到文件，新人不知道模板该放哪。

## 代码实战（对照源码）

### 1. `WebController`

`@RequestMapping("/web/index")` 返回 `"index"`，对应 `templates/index.ftl`。

### 2. 静态资源

`static/css/` 等由 Spring MVC 默认映射。

## 运行与验证

- 浏览器访问 http://localhost:8080/web/index 查看渲染页面。

## 动手练习

- 在 ftl 中循环输出列表；Controller 传入 `List<String>`。


## 关键代码说明

和 02-1 的 `@RestController` 不同，页面渲染用 `@Controller`，返回值是视图名。

```java
@Controller
@RequestMapping("/web")
public class WebController {

    @RequestMapping("index")
    public String index(ModelMap map) {
        logger.info("这里是controller");
        map.put("title", "hello world");
        return "index"; // 注意，不要在最前面加上/，linux下面会出错
    }
}
```

- 返回 `"index"` 时，FreeMarker 自动配置会到 `classpath:/templates/index.ftl` 找模板。前面加 `/` 在 Linux 上会被当成绝对路径，找不到文件。
- `map.put("title", ...)` 把数据放进模型。模板里用 `${title}` 取值。
- 方法没有 `@ResponseBody`，所以字符串不会原样输出到浏览器。

模板 `templates/index.ftl`：

```html
<link href="/css/index.css" rel="stylesheet" />
<h1 id="title">${title}</h1>
<script type="text/javascript" src="/webjars/jquery/2.1.4/jquery.min.js"></script>
```

- `${title}` 由 FreeMarker 在服务端替换成 Controller 传入的 `hello world`。
- `/css/...`、`/images/...` 对应 `src/main/resources/static/`，Spring MVC 默认把该目录映射到网站根路径。
- `/webjars/...` 来自 Maven 的 webjars 依赖，同样由静态资源处理器提供。

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
