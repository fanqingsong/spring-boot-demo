# spring-boot-demo-26-1：如何进行远程调试

> 工程目录：[`spring-boot-demo-26-1`](../../spring-boot-demo-26-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **如何进行远程调试** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- JVM 远程调试参数：`-agentlib:jdwp=...`
- IDE Remote Debug 连接已部署进程
- 本仓库仅 README，无业务源码

## 代码实战（对照源码）

### 1. `README.md`

该节以视频操作为主：启动脚本加入 debug 端口，IDE 配置 Host/Port attach。

## 运行与验证

- 本地 java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar app.jar，IDE 连接 5005。

## 动手练习

- 在断点处查看变量（仅限授权环境）。


## 关键源码路径

按下面路径在 IDE 中打开对照（相对各 demo 工程根目录）：
## 学习路径

- 上一模块：[`spring-boot-demo-25-1`](./spring-boot-demo-25-1.md)
- 下一模块：[`spring-boot-demo-27-1`](./spring-boot-demo-27-1.md)
