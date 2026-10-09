# spring-boot-demo-14-1：使用 SQL 关系型数据库 - Spring Data JPA

> 工程目录：[`spring-boot-demo-14-1`](../../spring-boot-demo-14-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **使用 SQL 关系型数据库 - Spring Data JPA** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- `spring-boot-starter-data-jpa`
- 实体 `@Entity` + Repository 接口继承 `JpaRepository`
- Hibernate 自动建表/更新（视配置而定）
- 在 13-1 基础上增加 JPA 实体 `RoncooUserLog`

## 代码实战（对照源码）

### 1. `RoncooUserLogDao`

Spring Data 接口，无需实现类。

### 2. `RoncooUserLog`

JPA 实体映射表。

## 运行与验证

- 启动观察 Hibernate DDL 日志；调用接口写入日志记录。

## 动手练习

- 在 Repository 中声明 `findByUserName` 方法名查询。


## 关键源码路径

按下面路径在 IDE 中打开对照（相对各 demo 工程根目录）：

**Java**

- `src/main/java/com/roncoo/example/SpringBootDemo141Application.java`
- `src/main/java/com/roncoo/example/bean/RoncooUser.java`
- `src/main/java/com/roncoo/example/bean/RoncooUserLog.java`
- `src/main/java/com/roncoo/example/controller/ApiController.java`
- `src/main/java/com/roncoo/example/controller/FileController.java`
- `src/main/java/com/roncoo/example/controller/WebController.java`
- `src/main/java/com/roncoo/example/dao/RoncooUserDao.java`
- `src/main/java/com/roncoo/example/dao/RoncooUserLogDao.java`
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

- 上一模块：[`spring-boot-demo-13-1`](./spring-boot-demo-13-1.md)
- 下一模块：[`spring-boot-demo-15-1`](./spring-boot-demo-15-1.md)
