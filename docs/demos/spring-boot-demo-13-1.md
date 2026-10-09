# spring-boot-demo-13-1：使用 SQL 关系型数据库 - JdbcTemplate

> 工程目录：[`spring-boot-demo-13-1`](../../spring-boot-demo-13-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **使用 SQL 关系型数据库 - JdbcTemplate** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

本节每个知识点从 **为何出现**、**解决什么问题**、**若没有会怎样** 三方面说明，便于理解「为什么要学这一项」，而不只是记名词。

- **`spring-boot-starter-jdbc` + MySQL 驱动**
  - **为何出现**：应用访问数据库是常态；JDBC 是 Java 标准，但裸 JDBC 连接管理易错。
  - **解决什么问题**：Boot 自动配 DataSource + JdbcTemplate，驱动单独声明即可连 MySQL。
  - **若没有会怎样**：自己 new Connection 不池化，高并发下连接耗尽；或忘记 close 导致泄漏。

- **手写 DAO + `JdbcTemplate`**
  - **为何出现**：JPA 对复杂 SQL/legacy 库有时过重；团队需要看清 SQL 与结果映射。
  - **解决什么问题**：模板方法执行 SQL、处理 ? 占位符和 RowMapper，比纯 JDBC 简洁又比 ORM 透明。
  - **若没有会怎样**：字符串拼接 SQL 易注入；或 JPA 生成 SQL 不可控，DBA 无法优化。

- **`JdbcDaoImpl` 抽象**
  - **为何出现**：多个 DAO 重复分页、count、列映射样板，违反 DRY。
  - **解决什么问题**：基类封装通用 JDBC 操作，子类只关心表字段与 SQL 片段。
  - **若没有会怎样**：每个 DAO 复制粘贴分页逻辑，改一页大小要改 N 处，bug 反复出现。

- **数据源在 profile 配置中**
  - **为何出现**：dev 连本地库、prod 连集群，URL/账号必须随环境变。
  - **解决什么问题**：与 04-1 一致，profile 文件放 jdbc 配置，避免误连生产。
  - **若没有会怎样**：jdbc url 写死在主配置，测试环境指向生产，一次误操作删库。

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


## 关键代码说明

### 数据源：配 URL 就会有 JdbcTemplate

```properties
spring.datasource.url=jdbc:mysql://localhost/spring_boot_demo?useUnicode=true&characterEncoding=utf-8
spring.datasource.username=root
spring.datasource.password=123456
```

`spring-boot-starter-jdbc` 看到这些属性后自动创建 `DataSource` 和 `JdbcTemplate`。库 `spring_boot_demo` 和表 `roncoo_user` 需要事先建好。

### DAO 实现直接写 SQL

```java
@Repository
public class RoncooUserDaoImpl extends JdbcDaoImpl implements RoncooUserDao {

    @Override
    public int insert(RoncooUser roncooUser) {
        String sql = "insert into roncoo_user (name, create_time) values (?, ?)";
        return jdbcTemplate.update(sql, roncooUser.getName(), roncooUser.getCreateTime());
    }

    @Override
    public RoncooUser selectById(int id) {
        String sql = "select * from roncoo_user where id=?";
        return queryForObject(sql, RoncooUser.class, id);
    }
}
```

- `?` 是占位符，参数由 `jdbcTemplate.update` 绑定，避免拼接用户输入。
- `update` 的返回值是影响行数。
- `jdbcTemplate` 字段在父类 `JdbcDaoImpl` 里 `@Autowired`，子类直接使用。

### 父类把 ResultSet 映射成 Bean

```java
public <T> T queryForObject(String sql, Class<T> clazz, Object... args) {
    return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<T>(clazz), args);
}
```

`BeanPropertyRowMapper` 按列名对应 setter。列 `create_time` 会映射到 `setCreateTime`（下划线转驼峰）。所以 `RoncooUser` 不需要手写 `RowMapper`。

分页方法 `queryForPage` 先用 `Sql.countSql` 查总数，再拼 `limit` 查当前页。`name` 模糊查询用 `Sql.checkSql` 过滤拼接内容，因为 `LIKE` 这段没有走 `?` 占位符。

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
