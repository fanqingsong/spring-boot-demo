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


## 关键代码说明

本目录没有业务源码。远程调试的关键是 JVM 参数，而不是 Spring 注解。

被调试的进程要先打开 JDWP 监听：

```bash
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar app.jar
```

- `transport=dt_socket`：用套接字传调试数据。
- `server=y`：这个进程是调试服务端，等 IDE 来连。
- `suspend=n`：没有调试器附着时也继续启动。改成 `y` 会停在启动处，直到 IDE 连上。
- `address=5005`：调试端口。IDE 里 Remote JVM Debug 的 Host/Port 填运行该 jar 的机器和 5005。

断点打在与这个 jar **同一份源码** 上。源码和 class 行号不一致时，断点会落在错误的行。只在你有权调试的机器上打开该端口。

## 关键源码路径

按下面路径在 IDE 中打开对照（相对各 demo 工程根目录）：
## 学习路径

- 上一模块：[`spring-boot-demo-25-1`](./spring-boot-demo-25-1.md)
- 下一模块：[`spring-boot-demo-27-1`](./spring-boot-demo-27-1.md)
