# spring-boot-demo-03-2：配置文件详解：YAML

> 工程目录：[`spring-boot-demo-03-2`](../../spring-boot-demo-03-2) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **配置文件详解：YAML** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

本节每个知识点从 **为何出现**、**解决什么问题**、**若没有会怎样** 三方面说明，便于理解「为什么要学这一项」，而不只是记名词。

- **YAML 层级缩进**
  - **为何出现**：properties 扁平键 `spring.datasource.url` 一长串，多层嵌套时重复前缀多、 eyeball 困难。
  - **解决什么问题**：用树形结构表达嵌套，适合 datasource、多模块配置，减少重复前缀。
  - **若没有会怎样**：大段配置可读性差，改错一个点号键就整项失效，review 时难 diff。

- **`.properties` 与 `.yaml` 并存**
  - **为何出现**：团队和个人习惯不同，迁移期可能两种格式共存；需要框架统一合并规则。
  - **解决什么问题**：Boot 同时解析两种格式并按优先级合并到同一 Environment，便于渐进迁移 03-1 → 03-2。
  - **若没有会怎样**：误以为只能二选一，或不知道哪份文件覆盖哪份，出现「我改了 yaml 为什么还是旧 port」类问题。

- **YAML 内 `${roncoo.name}`**
  - **为何出现**：YAML 里同样存在引用其它键、避免重复的需求，与 properties 机制应对齐。
  - **解决什么问题**：在 YAML 文档内组合配置，与 03-1 占位符语义一致，换格式不换思路。
  - **若没有会怎样**：YAML 里重复写死多份相同值，改应用名要改多处，和 properties 时代同样痛苦。

- **`spring.jackson.*`**
  - **为何出现**：API 返回的 `Date` 默认 ISO 或时间戳，与前端、日志、产品要求的「yyyy-MM-dd HH:mm:ss」不一致。
  - **解决什么问题**：全局统一 JSON 日期格式与时区，所有接口表现一致，前端少写特殊解析。
  - **若没有会怎样**：每个字段加 `@JsonFormat` 或前端各自解析，时区错乱、展示不一致，联调扯皮。

## 代码实战（对照源码）

### 1. `application.yaml` 与 `config/application.yaml`

使用 `roncoo:` 块、`server.port`、`spring.jackson` 配置；注意 `config/` 下端口示例为 8090。

### 2. Controller

与 03-1 类似，验证 YAML 绑定是否生效。

## 运行与验证

- 启动后确认实际监听端口（以激活的配置为准）。

## 动手练习

- 把 03-1 的 properties 完整迁移为 yaml 一份对比。


## 关键代码说明

YAML 和 03-1 的 properties 表达同一套配置，只是层级写法不同。Java 侧仍然是 `@Value("${roncoo.secret}")`，占位符路径用点号，和 YAML 缩进一一对应。

`config/application.yaml` 优先级更高（端口 9090），内容是：

```yaml
roncoo:
  secret: ${random.value}
  number: ${random.int}
  name: www.roncoo.com
  desc: the domain is ${roncoo.name}

server:
  port: 9090
```

对照关系：

| YAML | 等价的 properties 键 | 注入方式 |
| --- | --- | --- |
| `roncoo.secret` | `roncoo.secret` | `@Value("${roncoo.secret}")` |
| `roncoo.number` | `roncoo.number` | `@Value("${roncoo.number}")`，字段是 `int` |
| `roncoo.desc` | `roncoo.desc` | 先展开 `${roncoo.name}`，再注入 |
| `server.port` | `server.port` | 框架读取，决定 Tomcat 端口 |

外层 `application.yaml` 还配置了 Jackson：

```yaml
spring:
  jackson:
    date-format: yyyy-MM-dd HH:mm:ss
    time-zone: Asia/Chongqing
```

这等价于 `spring.jackson.date-format` 和 `spring.jackson.time-zone`。`config/application.yaml` 没有写这两项，所以日期格式仍以外层为准；端口被 `config` 覆盖成 9090。

`IndexController` 与 03-1 相同：`/index/get` 把 `secret`、`id`、`desc` 放进 JSON，用来核对 YAML 是否被正确解析。缩进错误时启动会直接失败，YAML 对空格敏感。

## 关键源码路径

按下面路径在 IDE 中打开对照（相对各 demo 工程根目录）：

**Java**

- `src/main/java/com/roncoo/education/SpringBootDemo32Application.java`
- `src/main/java/com/roncoo/education/bean/User.java`
- `src/main/java/com/roncoo/education/controller/IndexController.java`

**配置/模板**

- `src/main/resources/application.yaml`
- `src/main/resources/config/application.yaml`
## 学习路径

- 上一模块：[`spring-boot-demo-03-1`](./spring-boot-demo-03-1.md)
- 下一模块：[`spring-boot-demo-04-1`](./spring-boot-demo-04-1.md)
