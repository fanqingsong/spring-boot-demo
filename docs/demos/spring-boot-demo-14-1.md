# spring-boot-demo-14-1：使用 SQL 关系型数据库 - Spring Data JPA

> 工程目录：[`spring-boot-demo-14-1`](../../spring-boot-demo-14-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **使用 SQL 关系型数据库 - Spring Data JPA** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- **`spring-boot-starter-data-jpa`**：集成 Hibernate 与 Spring Data JPA，用实体映射表、用 Repository 接口完成 CRUD，多数 SQL 由框架生成。
- **`@Entity` + `JpaRepository`**：实体类对应表结构；Repository 继承 `JpaRepository<实体, 主键类型>` 即拥有 save/findAll 等，无需写实现类。
- **Hibernate DDL**：通过 `spring.jpa.hibernate.ddl-auto` 等控制在启动时自动建表/更新 schema（开发常用，生产需谨慎）。
- **`RoncooUserLog` 实体**：在 13-1 JDBC 用户表基础上增加 JPA 管理的日志表，演示同一项目中 JDBC 与 JPA 可并存（本模块侧重 JPA 侧）。

## 代码实战（对照源码）

### 1. `RoncooUserLogDao`

Spring Data 接口，无需实现类。

### 2. `RoncooUserLog`

JPA 实体映射表。

## 运行与验证

- 启动观察 Hibernate DDL 日志；调用接口写入日志记录。

## 动手练习

- 在 Repository 中声明 `findByUserName` 方法名查询。


## 关键代码说明

日志表改用 Spring Data JPA：实体上声明表映射，接口只声明方法名，SQL 由框架生成。用户表 `RoncooUserDao` 仍是上一讲的 JdbcTemplate。

### 实体

```java
@Entity
public class RoncooUserLog {
    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private Date createTime;
    @Column
    private String userName;
    @Column
    private String userIp;
}
```

`@Entity` 让这个类对应一张表（默认表名由类名推导）。`@Id` + `@GeneratedValue` 表示主键自增。字段需要 getter/setter，JPA 通过它们读写列。

### 仓库接口

```java
public interface RoncooUserLogDao extends JpaRepository<RoncooUserLog, Integer> {

    @Query(value = "select u from RoncooUserLog u where u.userName=?1")
    RoncooUserLog findByUserName(String string);

    RoncooUserLog findByUserNameAndUserIp(String string, String ip);

    Page<RoncooUserLog> findByUserName(String string, Pageable pageable);
}
```

- `JpaRepository<RoncooUserLog, Integer>` 已经提供 `save`、`findOne`、`delete` 等，不用写实现类。
- `findByUserNameAndUserIp` 没有 `@Query`。Spring Data 解析方法名：`findBy` + `UserName` + `And` + `UserIp`，生成按这两个字段查询的 SQL。
- 带 `@Query` 的 `findByUserName` 使用 JPQL（`select u from RoncooUserLog`），`?1` 是第一个参数。这里查的是实体属性 `userName`，不是表列名。
- 返回 `Page` 并多一个 `Pageable` 参数时，框架自动加分页，不用手写 `limit`。

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
