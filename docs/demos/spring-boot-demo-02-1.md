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
