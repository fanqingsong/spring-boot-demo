# spring-boot-demo-03-1：配置文件详解：Properties

> 工程目录：[`spring-boot-demo-03-1`](../../spring-boot-demo-03-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **配置文件详解：Properties** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- classpath 下 `application.properties` 与 `config/application.properties` 的加载顺序（config 子目录优先级更高）
- 自定义前缀属性 `roncoo.*`
- 占位符：`${random.value}`、`${random.int}`、`${roncoo.name}` 引用
- `@Value` 注入配置到 Controller 字段

## 代码实战（对照源码）

### 1. `config/application.properties`

定义 `roncoo.secret`、`roncoo.number`、`roncoo.desc` 等；`server.port=8080`。

### 2. `IndexController`

在 `/index/get` 响应中附带 `secret`、`id`、`desc`，验证配置已注入。

## 运行与验证

- 运行后访问 `/index/get?name=test`，确认返回中含随机 secret 与 desc 展开结果。
- 修改 `server.port` 为 8090，重启验证端口变化。

## 动手练习

- 增加 `roncoo.version=1.0` 并在接口中返回。


## 关键源码路径

按下面路径在 IDE 中打开对照（相对各 demo 工程根目录）：

**Java**

- `src/main/java/com/roncoo/education/SpringBootDemo31Application.java`
- `src/main/java/com/roncoo/education/bean/User.java`
- `src/main/java/com/roncoo/education/controller/IndexController.java`

**配置/模板**

- `src/main/resources/application.properties`
- `src/main/resources/config/application.properties`
## 学习路径

- 上一模块：[`spring-boot-demo-02-1`](./spring-boot-demo-02-1.md)
- 下一模块：[`spring-boot-demo-03-2`](./spring-boot-demo-03-2.md)
