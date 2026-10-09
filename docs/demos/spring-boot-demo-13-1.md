# spring-boot-demo-13-1：使用 SQL 关系型数据库 - JdbcTemplate

> 工程目录：[`spring-boot-demo-13-1`](../../spring-boot-demo-13-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **使用 SQL 关系型数据库 - JdbcTemplate** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- `spring-boot-starter-jdbc` + MySQL 驱动
- 手写 DAO + `JdbcTemplate` 执行 SQL
- `JdbcDaoImpl` 抽象分页与通用 JDBC 操作
- 数据源在 profile 配置文件中

## 代码实战（对照源码）

### 1. `RoncooUserDaoImpl`

继承 `JdbcDaoImpl`，实现 insert/update/query。

### 2. `Sql` / `Page`

拼接 SQL 与分页辅助。

### 3. `application-dev.properties`

MySQL URL、用户名密码。

## 运行与验证

- 本地创建库 spring_boot_demo，改配置后启动；通过 Web/API 触发 CRUD。

## 动手练习

- 为查询增加按 name 模糊搜索接口。


## 关键源码路径

按下面路径在 IDE 中打开对照（相对各 demo 工程根目录）：

**Java**

- `src/main/java/com/roncoo/example/SpringBootDemo131Application.java`
- `src/main/java/com/roncoo/example/bean/RoncooUser.java`
- `src/main/java/com/roncoo/example/controller/ApiController.java`
- `src/main/java/com/roncoo/example/controller/FileController.java`
- `src/main/java/com/roncoo/example/controller/WebController.java`
- `src/main/java/com/roncoo/example/dao/RoncooUserDao.java`
- `src/main/java/com/roncoo/example/dao/impl/RoncooUserDaoImpl.java`
- `src/main/java/com/roncoo/example/handler/BizExcepiton.java`
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

- 上一模块：[`spring-boot-demo-12-1`](./spring-boot-demo-12-1.md)
- 下一模块：[`spring-boot-demo-14-1`](./spring-boot-demo-14-1.md)
