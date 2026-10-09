# spring-boot-demo-04-2：配置文件 - 多环境配置（YAML）

> 工程目录：[`spring-boot-demo-04-2`](../../spring-boot-demo-04-2) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **配置文件 - 多环境配置（YAML）** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- YAML 多文档：用 `---` 分隔不同 profile 段
- 在同文件内用 `spring.profiles: dev` 绑定段落
- 适合把多环境端口等收拢到一个文件

## 代码实战（对照源码）

### 1. `application.yaml`

顶部 `spring.profiles.active: dev`；后续 `---` 块分别定义 dev/prod/test 的 `server.port`。

## 运行与验证

- 切换 active 验证 8080/8081/8082 等端口。

## 动手练习

- 练习把 04-1 的 properties 多环境改写为本讲 YAML 风格。


## 关键源码路径

按下面路径在 IDE 中打开对照（相对各 demo 工程根目录）：

**Java**

- `src/main/java/com/roncoo/education/SpringBootDemo42Application.java`
- `src/main/java/com/roncoo/education/bean/User.java`
- `src/main/java/com/roncoo/education/controller/IndexController.java`

**配置/模板**

- `src/main/resources/application.yaml`
## 学习路径

- 上一模块：[`spring-boot-demo-04-1`](./spring-boot-demo-04-1.md)
- 下一模块：[`spring-boot-demo-05-1`](./spring-boot-demo-05-1.md)
