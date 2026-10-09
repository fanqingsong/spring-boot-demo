# spring-boot-demo-16-1：使用 SQL 关系型数据库 - H2 嵌入式数据库

> 工程目录：[`spring-boot-demo-16-1`](../../spring-boot-demo-16-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **使用 SQL 关系型数据库 - H2 嵌入式数据库** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

本节每个知识点从 **为何出现**、**解决什么问题**、**若没有会怎样** 三方面说明，便于理解「为什么要学这一项」，而不只是记名词。

- **H2 数据库**
  - **为何出现**：教程/CI 不能要求每人装 MySQL；嵌入式库让 JVM 进程内即有 SQL 引擎。
  - **解决什么问题**：内存或文件模式 H2，零安装跑通 JDBC/JPA 全链路。
  - **若没有会怎样**：新人卡在装 MySQL、建库授权，还没学 Spring 就先被环境劝退。

- **切换数据源配置**
  - **为何出现**：持久层 API 应独立于具体数据库产品，换库只换配置。
  - **解决什么问题**：改 URL/driver 为 H2，DAO/Repository 不变，验证分层正确性。
  - **若没有会怎样**：SQL 写满 MySQL 方言，换 H2 测试跑不起来，误以为「只能连 MySQL 开发」。

- **适用场景**
  - **为何出现**：H2 不是银弹，需要知道边界避免误上生产。
  - **解决什么问题**：明确：本地/单测/原型用 H2，生产用 MySQL 等，各取所长。
  - **若没有会怎样**：生产用 H2 内存库，重启数据全丢；或并发能力不够导致 demo 思维带入上线。

## 代码实战（对照源码）

### 1. `pom.xml`

引入 `h2` 依赖。

### 2. 配置文件

H2 URL 与 JPA ddl-auto 等。

## 运行与验证

- 不启 MySQL 也可启动；访问 h2 console（若开启）查看表数据。

## 动手练习

- 对比 15-1 与 16-1 数据源配置差异。


## 关键代码说明

DAO、Service、`@Transactional` 与 15-1 相同。本讲只换数据源，让本地不必安装 MySQL。

```properties
spring.datasource.url=jdbc:h2:file:D:/roncoo_h2/roncoo_spring_boot;AUTO_SERVER=TRUE;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.username=roncoo
spring.datasource.password=roncoo

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

- `jdbc:h2:file:...` 使用文件库，数据在进程退出后还在。改成 `jdbc:h2:mem:testdb` 则是内存库，重启即清空。
- `AUTO_SERVER=TRUE` 允许其它进程同时连这个文件库。
- `ddl-auto=update` 按实体 `RoncooUserLog` 自动建表或补列。JdbcTemplate 使用的 `roncoo_user` 不会被 JPA 建出来，仍需已有表结构，或自己在 H2 里建表。
- `show-sql=true` 把 JPA 生成的 SQL 打到日志，便于对照 `findBy...` 方法名。

pom 中增加 `h2` 依赖后，Spring Boot 按 URL 选择驱动，`UserService.register` 的调用方式不变。

## 关键源码路径

按下面路径在 IDE 中打开对照（相对各 demo 工程根目录）：

**Java**

- `src/main/java/com/roncoo/example/SpringBootDemo161Application.java`
- `src/main/java/com/roncoo/example/bean/RoncooUser.java`
- `src/main/java/com/roncoo/example/bean/RoncooUserLog.java`
- `src/main/java/com/roncoo/example/controller/ApiController.java`
- `src/main/java/com/roncoo/example/controller/FileController.java`
- `src/main/java/com/roncoo/example/controller/WebController.java`
- `src/main/java/com/roncoo/example/dao/RoncooUserDao.java`
- `src/main/java/com/roncoo/example/dao/RoncooUserLogDao.java`
- `src/main/java/com/roncoo/example/dao/impl/RoncooUserDaoImpl.java`
- `src/main/java/com/roncoo/example/handler/BizExcepiton.java`
- `src/main/java/com/roncoo/example/service/UserService.java`
- `src/main/java/com/roncoo/example/util/base/JdbcDaoImpl.java`
- `src/main/java/com/roncoo/example/util/base/Page.java`
- `src/main/java/com/roncoo/example/util/base/Sql.java`
- `src/main/java/com/roncoo/example/util/configuration/CustomCorsConfiguration.java`
- `src/main/java/com/roncoo/example/util/configuration/CustomCorsConfiguration2.java`
- `src/main/java/com/roncoo/example/util/filter/CustomFilter.java`
- `src/main/java/com/roncoo/example/util/listerner/CustomListener.java`
- `src/main/java/com/roncoo/example/util/servlet/CustomServlet.java`

**配置/模板**

- `src/main/resources/application-dev.properties`
- `src/main/resources/application-prod.properties`
- `src/main/resources/application-test.properties`
- `src/main/resources/application.properties`
- `src/main/resources/logback-roncoo.xml`
- `src/main/resources/public/error/404.html`
- `src/main/resources/templates/error/500.ftl`
- `src/main/resources/templates/error/5xx.ftl`
- `src/main/resources/templates/error/error.ftl`
- `src/main/resources/templates/index.ftl`
## 学习路径

- 上一模块：[`spring-boot-demo-15-1`](./spring-boot-demo-15-1.md)
- 下一模块：[`spring-boot-demo-17-1`](./spring-boot-demo-17-1.md)
