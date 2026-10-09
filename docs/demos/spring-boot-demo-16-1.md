# spring-boot-demo-16-1：使用 SQL 关系型数据库 - H2 嵌入式数据库

> 工程目录：[`spring-boot-demo-16-1`](../../spring-boot-demo-16-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **使用 SQL 关系型数据库 - H2 嵌入式数据库** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- H2 内存/文件数据库，便于本地演示无需 MySQL
- 切换数据源为 H2 后 JPA/Jdbc 用法不变
- 适合单元测试与快速原型

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
