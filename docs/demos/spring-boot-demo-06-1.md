# spring-boot-demo-06-1：WEB 应用开发 - 模板引擎 FreeMarker

> 工程目录：[`spring-boot-demo-06-1`](../../spring-boot-demo-06-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **WEB 应用开发 - 模板引擎 FreeMarker** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- **`spring-boot-starter-freemarker`**：自动配置 FreeMarker 引擎与 Spring MVC 视图解析器，Controller 返回视图名即可渲染 `.ftl` 模板为 HTML。
- **`@Controller`（非 RestController）**：方法返回值当作**视图逻辑名**，由 ViewResolver 找模板；与 `@RestController` 直接写 JSON 响应体是两种 Web 风格。
- **`ModelMap` / `Model`**：在跳转视图前把键值对放入模型，模板里用 `${title}` 等占位符展示，实现服务端渲染页面。
- **模板路径 `classpath:/templates/*.ftl`**：Boot 约定默认目录与后缀，返回 `"index"` 即解析为 `templates/index.ftl`，无需 web.xml 或手动配置 ViewResolver。

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
