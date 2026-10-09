# spring-boot-demo-03-1：配置文件详解：Properties

> 工程目录：[`spring-boot-demo-03-1`](../../spring-boot-demo-03-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **配置文件详解：Properties** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- **配置加载顺序**：Spring Boot 会按固定顺序加载多处 `application.properties`；`classpath:config/` 下的文件优先级高于根目录，后加载的同键会覆盖先加载的，便于在不改默认文件的情况下本地覆盖。
- **自定义前缀 `roncoo.*`**：任意业务或演示用配置键，与框架的 `spring.*`、`server.*` 一样注入到 Environment；前缀只是命名约定，便于分组和 `@ConfigurationProperties` 绑定。
- **占位符 `${...}`**：在配置文件中引用其它键或内置随机值（如 `${random.value}`、`${roncoo.name}`），启动时由 Spring 解析成最终字符串，避免重复写死相同内容。
- **`@Value`**：把 Environment 中的单个配置项注入到 Bean 字段或方法参数；本模块在 Controller 中用来验证配置是否已成功读入并在接口响应中展示。

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


## 关键代码说明

本讲的重点是「配置写在哪里、谁覆盖谁、怎么注入到 Java」。

### 两份 properties，后加载的覆盖先加载的

`src/main/resources/application.properties` 里写了 `server.port=8090`，注释标明优先级较低。同名的 `src/main/resources/config/application.properties` 写了 `server.port=8080`，注释标明优先级较高。

Spring Boot 会同时加载这两处，`config/` 目录下的文件优先级更高，所以实际端口是 **8080**，不是 8090。改端口时要改生效的那一份。

自定义属性用占位符，在 `config/application.properties` 中：

```properties
roncoo.secret=${random.value}
roncoo.number=${random.int}
roncoo.name=www.roncoo.com
roncoo.desc=the domain is ${roncoo.name}
server.port=8080
```

- `${random.value}`、`${random.int}` 在启动时由 Spring Boot 填成随机值，每次重启都会变。
- `${roncoo.name}` 引用同一份配置里已经定义的键，展开后 `roncoo.desc` 是 `the domain is www.roncoo.com`。

外层 `application.properties` 里还有 Jackson 日期格式，这是公共配置，`config/` 没有覆盖它们，所以仍然生效：

```properties
spring.jackson.date-format=yyyy-MM-dd HH:mm:ss
spring.jackson.time-zone=Asia/Chongqing
```

`User.date` 序列化成 JSON 时就会按这个格式和时区输出。

### `@Value` 把配置注入字段

```java
@Value(value = "${roncoo.secret}")
private String secret;

@Value(value = "${roncoo.number}")
private int id;

@Value(value = "${roncoo.desc}")
private String desc;
```

容器创建 `IndexController` 时，按占位符从 Environment 取值。`roncoo.number` 是整数随机值，字段类型是 `int`，类型对不上会启动失败。

`/index/get` 把这三个字段放进 Map 返回，用来确认注入的是 `config/` 里那份（`desc` 以 `the domain is` 开头），而不是外层文件里的 `is a domain name`。

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
