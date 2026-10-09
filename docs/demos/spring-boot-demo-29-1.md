# spring-boot-demo-29-1：Spring Boot 集成 Druid

> 工程目录：[`spring-boot-demo-29-1`](../../spring-boot-demo-29-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **Spring Boot 集成 Druid** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- Alibaba Druid 连接池：监控 SQL
- `DruidConfiguration` 绑定 spring.datasource.druid
- `DruidWebStatFilter` + 监控 Servlet

## 代码实战（对照源码）

### 1. 数据源 Bean

替换默认连接池为 DruidDataSource。

### 2. 监控

访问 /druid 登录页查看 SQL 统计（以项目配置为准）。

## 运行与验证

- 启动后打开 Druid 控制台；执行几条 SQL 看监控面板。

## 动手练习

- 配置慢 SQL 日志阈值。


## 关键代码说明

### 用 Druid 替换默认连接池

```properties
spring.datasource.url=jdbc:mysql://localhost/spring_boot_demo?useUnicode=true&characterEncoding=utf-8
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.type=com.alibaba.druid.pool.DruidDataSource

spring.datasource.druid.initial-size=8
spring.datasource.druid.min-idle=5
spring.datasource.druid.max-active=10
spring.datasource.druid.filters=stat,config
```

`spring.datasource.type` 指定池实现。`initial-size`、`max-active` 是 Druid 自己的池参数，前缀 `spring.datasource.druid` 对应配置类上的 `@ConfigurationProperties("spring.datasource.druid")`。

```java
@Bean
@ConfigurationProperties("spring.datasource.druid")
public DruidDataSource dataSource(DataSourceProperties properties) {
    DruidDataSource druidDataSource = (DruidDataSource) properties
        .initializeDataSourceBuilder().type(DruidDataSource.class).build();
    DatabaseDriver databaseDriver = DatabaseDriver.fromJdbcUrl(properties.determineUrl());
    String validationQuery = databaseDriver.getValidationQuery();
    if (validationQuery != null) {
        druidDataSource.setValidationQuery(validationQuery);
    }
    return druidDataSource;
}
```

`initializeDataSourceBuilder` 先填入 url、用户名、密码和 `DruidDataSource` 类型。`@ConfigurationProperties` 再把 `initial-size` 等绑定到同名 setter。`validationQuery` 按 JDBC URL 选择（MySQL 一般是 `SELECT 1`），用来检测连接是否还活着。外层条件 `@ConditionalOnProperty(name = "spring.datasource.type", havingValue = "com.alibaba.druid.pool.DruidDataSource")` 保证只有声明了 Druid 才注册这个 Bean。

### 监控页

```java
@WebServlet(urlPatterns = { "/druid/*" }, initParams = {
    @WebInitParam(name = "loginUsername", value = "roncoo"),
    @WebInitParam(name = "loginPassword", value = "roncoo")
})
public class DruidStatViewServlet extends StatViewServlet {
}
```

启动类有 `@ServletComponentScan`，所以上面的 Servlet 会注册。浏览器打开 `/druid/` 用 initParam 里的账号登录，可以看到 SQL 统计。`filters=stat` 才会收集这些统计。

### MyBatis XML

`RoncooUserMapper.xml` 的 `namespace` 必须是接口全名 `com.roncoo.education.mapper.RoncooUserMapper`。`<resultMap>` 把列 `create_time` 映射到属性 `createTime`。接口方法名要和 XML 里 `<select id="...">` 的 id 一致，否则运行时找不到语句。

## 关键源码路径

按下面路径在 IDE 中打开对照（相对各 demo 工程根目录）：

**Java**

- `src/main/java/com/roncoo/education/SpringBootDemo291Application.java`
- `src/main/java/com/roncoo/education/bean/RoncooUser.java`
- `src/main/java/com/roncoo/education/bean/RoncooUserExample.java`
- `src/main/java/com/roncoo/education/bean/RoncooUserLog.java`
- `src/main/java/com/roncoo/education/bean/RoncooUserLogExample.java`
- `src/main/java/com/roncoo/education/controller/ApiController.java`
- `src/main/java/com/roncoo/education/mapper/RoncooUserLogMapper.java`
- `src/main/java/com/roncoo/education/mapper/RoncooUserMapper.java`
- `src/main/java/com/roncoo/education/util/configuration/DruidConfiguration.java`
- `src/main/java/com/roncoo/education/util/filter/DruidWebStatFilter.java`
- `src/main/java/com/roncoo/education/util/servlet/DruidStatViewServlet.java`

**配置/模板**

- `src/main/resources/application.properties`
- `src/main/resources/druid-bean.xml`
- `src/main/resources/mybatis/RoncooUserLogMapper.xml`
- `src/main/resources/mybatis/RoncooUserMapper.xml`
## 学习路径

- 上一模块：[`spring-boot-demo-28-1`](./spring-boot-demo-28-1.md)
- 下一模块：[`spring-boot-demo-30-1`](./spring-boot-demo-30-1.md)
