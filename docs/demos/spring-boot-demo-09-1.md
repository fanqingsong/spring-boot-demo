# spring-boot-demo-09-1：WEB 应用开发 - 错误处理

> 工程目录：[`spring-boot-demo-09-1`](../../spring-boot-demo-09-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **WEB 应用开发 - 错误处理** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- **默认 `/error`**：Spring Boot 内置错误处理控制器，未捕获异常或未找到资源时会转发到 `/error`，可返回 Whitelabel 页或自定义页。
- **`@ControllerAdvice` + `@ExceptionHandler`**：在全局统一捕获指定异常类型，决定返回 JSON 还是视图，避免每个 Controller 重复 try-catch。
- **`templates/error/` 自定义错误视图**：按状态码或错误名放置 FreeMarker 模板（如 `500.ftl`），给用户友好的错误页而非堆栈。
- **`BizExcepiton`（全局处理类）**：本模块示例把异常信息放入 Model 再返回 `error/500` 等视图，演示如何把技术错误转化为页面可展示的内容。

## 代码实战（对照源码）

### 1. `BizExcepiton`

捕获 `RuntimeException`/`Exception`，返回 `error/500` 等视图。

### 2. `WebController`

提供触发错误的测试路径（如 `/web/error`）。

## 运行与验证

- 故意访问会抛异常的 URL，查看自定义错误页内容。

## 动手练习

- 增加 `@ExceptionHandler` 处理自定义业务异常类。


## 关键代码说明

错误处理分两条线：自己抛出的异常，以及容器产生的 404/500。

### 故意抛异常

```java
@RequestMapping("error")
public String error(ModelMap map) {
    throw new RuntimeException("测试异常");
}
```

访问 `/web/error` 会抛 `RuntimeException`。没有处理时，用户会看到 Spring Boot 默认错误页。

### `@ControllerAdvice` 接住异常

```java
@ControllerAdvice
public class BizExcepiton {

    @ExceptionHandler({ RuntimeException.class })
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView processException(RuntimeException exception) {
        ModelAndView m = new ModelAndView();
        m.addObject("roncooException", exception.getMessage());
        m.setViewName("error/500");
        return m;
    }
}
```

- `@ControllerAdvice` 对所有 Controller 生效，不用在每个方法上写 try/catch。
- `@ExceptionHandler(RuntimeException.class)` 匹配这次抛出的异常。类里还有一个处理 `Exception` 的方法，更具体的 `RuntimeException` 会先被上面这个方法接住。
- `setViewName("error/500")` 对应 `templates/error/500.ftl`。模板里 `${roncooException}` 就是 `测试异常`。
- `@ResponseStatus(HttpStatus.OK)` 把状态码改成 200。浏览器看到的是错误文案，但 HTTP 状态是成功。生产环境通常应保持 500，这里是为了演示可以改写状态码。

### 容器级错误页

`templates/error/5xx.ftl` 使用 Spring Boot 错误属性 `${exception}`，给未被 `@ExceptionHandler` 吃掉的 5xx 使用。`public/error/404.html` 是静态 404 页，找不到 Controller 映射时由错误机制返回。

## 关键源码路径

按下面路径在 IDE 中打开对照（相对各 demo 工程根目录）：

**Java**

- `src/main/java/com/roncoo/example/SpringBootDemo91Application.java`
- `src/main/java/com/roncoo/example/controller/WebController.java`
- `src/main/java/com/roncoo/example/handler/BizExcepiton.java`

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

- 上一模块：[`spring-boot-demo-08-1`](./spring-boot-demo-08-1.md)
- 下一模块：[`spring-boot-demo-10-1`](./spring-boot-demo-10-1.md)
