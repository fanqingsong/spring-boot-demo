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
