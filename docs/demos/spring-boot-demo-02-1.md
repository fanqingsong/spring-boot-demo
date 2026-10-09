# spring-boot-demo-02-1：RESTful API 简单项目的快速搭建

> 工程目录：[`spring-boot-demo-02-1`](../../spring-boot-demo-02-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **RESTful API 简单项目的快速搭建** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- Spring Boot 最小 Web 依赖：`spring-boot-starter-web` 内嵌 Tomcat
- `@SpringBootApplication` 组合了配置、自动装配与组件扫描
- `@RestController` 返回值直接作为 HTTP 响应体（JSON/字符串）
- `@RequestParam` 绑定查询参数 / 表单参数
- `@PathVariable` 绑定 URL 路径片段
- `spring-boot-devtools` 开发热重启（可选）

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
