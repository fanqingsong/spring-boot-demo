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
