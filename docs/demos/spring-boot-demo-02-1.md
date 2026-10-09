# spring-boot-demo-02-1：RESTful API 简单项目的快速搭建

> 工程目录：[`spring-boot-demo-02-1`](../../spring-boot-demo-02-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **RESTful API 简单项目的快速搭建** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

本节每个知识点从 **为何出现**、**解决什么问题**、**若没有会怎样** 三方面说明，便于理解「为什么要学这一项」，而不只是记名词。

- **`spring-boot-starter-web`**
  - **为何出现**：传统 Java Web 需要单独安装 Tomcat、手写大量 XML 才能把 Servlet/Spring 跑起来，部署和依赖版本也容易打架。
  - **解决什么问题**：一条 Maven 依赖拉齐 Web 栈 + 内嵌容器，本地 `mvn spring-boot:run` 或 `java -jar` 就能对外提供 HTTP 服务。
  - **若没有会怎样**：你得自己装外置 Tomcat、配 WAR、对齐 Spring 与 Servlet 版本，一个 REST 小项目也要很多样板步骤，学习曲线陡。

- **`@SpringBootApplication`**
  - **为何出现**：Spring 项目过去要拆成多个 XML/Java 配置：扫哪些包、开哪些自动配置、哪里是配置类，新人常漏配导致 Bean 找不到。
  - **解决什么问题**：一个注解等价于「配置 + 自动装配 + 组件扫描」，启动类即入口，约定大于配置。
  - **若没有会怎样**：启动类要写多段注解或 XML，Controller 放错包就不会注册，排查「404 / No mapping」会浪费大量时间。

- **`@RestController`**
  - **为何出现**：前后端分离和移动端兴起后，接口主要返回 JSON 而不是 JSP 页面；需要明确区分「返回视图」和「返回数据」。
  - **解决什么问题**：方法返回值直接序列化为响应体（JSON/文本），适合 REST API，不用写 `@ResponseBody` 每个方法。
  - **若没有会怎样**：若误用 `@Controller`，返回值会被当成视图名去找模板，接口返回 404 或 Whitelabel，客户端拿不到 JSON。

- **`@RequestParam`**
  - **为何出现**：HTTP 查询串和表单字段是浏览器与客户端最常用的传参方式，框架需要把字符串安全绑定到 Java 类型。
  - **解决什么问题**：自动从 `?name=xx` 或表单解析参数并做类型转换，少写 `request.getParameter` 和手动解析。
  - **若没有会怎样**：只能手写 Servlet API 取参，易漏编码、类型转换和必填校验，代码冗长且易出 NPE。

- **`@PathVariable`**
  - **为何出现**：REST 风格把资源标识放在 URL 路径里（如 `/users/1`），语义清晰且利于缓存与网关路由。
  - **解决什么问题**：把路径片段绑定到方法参数，URL 更简洁，符合 REST 资源定位习惯。
  - **若没有会怎样**：只能把所有参数堆在 query string，URL 冗长、不符合 REST 约定，网关和日志分析也不直观。

- **`spring-boot-devtools`（可选）**
  - **为何出现**：开发时频繁改代码重启全量 Tomcat 很慢，反馈循环长，影响效率。
  - **解决什么问题**：classpath 变更时自动重启（或 LiveReload 静态资源），改一行 Controller 几秒内可验证。
  - **若没有会怎样**：每次改代码都要手动停启进程，demo 阶段尚可，真实项目日常开发会非常拖慢节奏（生产也不应启用它）。

## 代码实战（对照源码）

### 1. 启动类 `SpringBootDemo21Application`

仅包含 `@SpringBootApplication` 与 `main`，调用 `SpringApplication.run` 启动内嵌容器。

### 2. `IndexController`

`@RequestMapping("/index")` 为类级前缀。`index()` 返回纯文本；`get(name)` 返回 `HashMap`，Spring MVC 自动序列化为 JSON；`getUser(id,name)` 演示路径变量并封装为 `User` 对象返回。

### 3. `User` Bean

普通 POJO，作为 JSON 响应字段（id、name、date）。

## 运行与验证

- cd spring-boot-demo-02-1 && mvn spring-boot:run
- curl http://localhost:8080/index
- curl 'http://localhost:8080/index/get?name=roncoo'
- curl http://localhost:8080/index/get/1/zhangsan

## 动手练习

- 新增 `/index/hello` 接口，返回带时间戳的 JSON。
- 给 `User` 增加 `email` 字段，观察 JSON 变化。


## 关键代码说明

本讲只有三处代码，合在一起就是一个能跑起来的 REST 服务。

### 启动类：一行完成容器启动

`SpringBootDemo21Application` 没有手写 Tomcat、没有 XML。`@SpringBootApplication` 同时打开自动配置、组件扫描和 `@Configuration`。`SpringApplication.run` 会创建 Spring 容器并启动内嵌 Tomcat。

```java
@SpringBootApplication
public class SpringBootDemo21Application {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemo21Application.class, args);
    }
}
```

启动类所在包是 `com.roncoo.education`，默认只扫描这个包及其子包。Controller、Bean 必须放在它下面，否则不会被注册。

### Controller：三种返回方式

```java
@RestController
@RequestMapping(value = "/index")
public class IndexController {

    @RequestMapping
    public String index() {
        return "hello world";
    }

    @RequestMapping(value = "/get")
    public HashMap<String, Object> get(@RequestParam String name) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("title", "hello world");
        map.put("name", name);
        return map;
    }

    @RequestMapping(value = "/get/{id}/{name}")
    public User getUser(@PathVariable int id, @PathVariable String name) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setDate(new Date());
        return user;
    }
}
```

- `@RestController` = `@Controller` + `@ResponseBody`。方法返回值直接写进 HTTP 响应，不会去找模板。
- 类上的 `@RequestMapping("/index")` 是前缀，三个方法分别对应 `/index`、`/index/get`、`/index/get/{id}/{name}`。
- `index()` 返回 `String`，响应体就是纯文本 `hello world`。
- `get` 返回 `HashMap`，Spring MVC 用 Jackson 把它序列化成 JSON。`@RequestParam String name` 绑定查询参数，缺了 `name` 会 400。
- `getUser` 的 `{id}`、`{name}` 是路径变量，由 `@PathVariable` 按名字注入。返回 `User` 同样走 JSON。

### User：普通 JavaBean

`User` 只有 `id`、`name`、`date` 和 getter/setter。Jackson 按 getter 生成 JSON 字段名。这里没有 JPA 注解，它只是响应对象。

## 关键源码路径

按下面路径在 IDE 中打开对照（相对各 demo 工程根目录）：

**Java**

- `src/main/java/com/roncoo/education/SpringBootDemo21Application.java`
- `src/main/java/com/roncoo/education/bean/User.java`
- `src/main/java/com/roncoo/education/controller/IndexController.java`

**配置/模板**

- `src/main/resources/application.properties`
## 学习路径

- 上一模块：无（可从本仓库 README 开始）
- 下一模块：[`spring-boot-demo-03-1`](./spring-boot-demo-03-1.md)
