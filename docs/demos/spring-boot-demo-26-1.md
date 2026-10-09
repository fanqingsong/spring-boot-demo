# spring-boot-demo-26-1：如何进行远程调试

> 工程目录：[`spring-boot-demo-26-1`](../../spring-boot-demo-26-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **如何进行远程调试** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

本节每个知识点从 **为何出现**、**解决什么问题**、**若没有会怎样** 三方面说明，便于理解「为什么要学这一项」，而不只是记名词。

- **JDWP 远程调试参数**
  - **为何出现**：测试/预发问题往往无法本地复现，需要在运行中的 JVM 上断点看变量。
  - **解决什么问题**：JDWP 让 JVM 监听调试端口，IDE attach 而不停止进程（suspend=n）。
  - **若没有会怎样**：只能加日志重新发版，周期长；或 `System.out` 猜状态，难查并发 bug。

- **IDE Remote Debug**
  - **为何出现**：命令行 jdb 难用；IDE 可视化断点、变量、调用栈是日常工具。
  - **解决什么问题**：配置 Remote 连接到 5005 等端口，与本地 Debug 体验一致。
  - **若没有会怎样**：生产误开 debug 端口被攻击；或不会 attach，线上问题只能盲改。

- **本仓库无业务源码**
  - **为何出现**：26 模块重点是运维流程，不是 Spring API；文档需强调安全边界。
  - **解决什么问题**：说明：调试是环境能力，仅内网、临时开启、用完关闭，与业务代码无关。
  - **若没有会怎样**：长期暴露 JDWP 等于远程代码执行风险；或以为必须改 Spring 才能远程调试。

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
