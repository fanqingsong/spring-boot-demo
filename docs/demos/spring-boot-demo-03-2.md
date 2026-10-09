# spring-boot-demo-03-2：配置文件详解：YAML

> 工程目录：[`spring-boot-demo-03-2`](../../spring-boot-demo-03-2) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **配置文件详解：YAML** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- YAML 层级缩进表示配置树，等价于 properties 的点号键
- Spring Boot 同时支持 `.properties` 与 `.yaml`（同键时遵循优先级规则）
- YAML 中 `${roncoo.name}` 引用同文件其他键
- `spring.jackson.*` 全局 JSON 日期格式与时区

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
