# spring-boot-demo-03-1：配置文件详解：Properties

> 工程目录：[`spring-boot-demo-03-1`](../../spring-boot-demo-03-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **配置文件详解：Properties** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

本节每个知识点从 **为何出现**、**解决什么问题**、**若没有会怎样** 三方面说明，便于理解「为什么要学这一项」，而不只是记名词。

- **配置加载顺序**
  - **为何出现**：配置可能来自 jar 内、jar 外、环境变量等多处；若没有统一优先级，同一键谁生效会混乱。
  - **解决什么问题**：Spring Boot 规定加载顺序（如 `config/` 子目录覆盖根目录），团队可约定「默认配置 + 本地覆盖」而不改仓库文件。
  - **若没有会怎样**：本地改 port 只能改提交到 Git 的主文件，容易误提交个人密码；或不知道为何改了配置却不生效。

- **自定义前缀 `roncoo.*`**
  - **为何出现**：框架键 `spring.*` 与业务键混在一起难维护；大型项目需要把「自己的配置」和「框架配置」分开命名。
  - **解决什么问题**：用统一前缀分组业务参数，便于 `@ConfigurationProperties(prefix="roncoo")` 整组绑定和文档化。
  - **若没有会怎样**：业务配置散落无前缀，键名冲突、难以批量注入，重构时全局搜索也不安全。

- **占位符 `${...}`**
  - **为何出现**：同一值在多处重复（如应用名、描述模板）时，改一处要改多处，易不一致。
  - **解决什么问题**：引用其它键或 `random.*` 生成启动期随机值，DRY 且支持组合字符串（如 desc 拼接 name）。
  - **若没有会怎样**：复制粘贴导致 secret、URL 多处不一致；无法利用 Boot 内置 random 做演示级动态配置。

- **`@Value`**
  - **为何出现**：业务代码需要从配置读端口、开关、密钥，若到处 `Environment.getProperty` 则样板代码多。
  - **解决什么问题**：声明式把单个键注入字段，Controller/Service 直接使用，与配置中心解耦（键名在 properties 改即可）。
  - **若没有会怎样**：硬编码 magic number/string 在 Java 里，换环境要重新编译；测试也难以替换配置。

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
