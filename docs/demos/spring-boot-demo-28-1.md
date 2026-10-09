# spring-boot-demo-28-1：Spring Boot 集成 MyBatis

> 工程目录：[`spring-boot-demo-28-1`](../../spring-boot-demo-28-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **Spring Boot 集成 MyBatis** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- `mybatis-spring-boot-starter`
- Mapper 接口 + XML SQL 映射
- 包名 com.roncoo.education，与前期 example 项目并列演进

## 代码实战（对照源码）

### 1. `RoncooUserMapper.java` + XML

namespace 与接口全限定名一致。

### 2. 启动类

扫描 Mapper。

## 运行与验证

- 配置 MySQL；调用 Controller 走 MyBatis 查询用户。

## 动手练习

- 增加动态 SQL if 条件查询。


## 关键代码说明

本模块是独立的小工程（包名回到 `com.roncoo.education`）。MyBatis 用注解把 SQL 写在 Mapper 接口上，没有 XML。

```java
@Mapper
public interface RoncooUserMapper {

    @Insert(value = "insert into roncoo_user (name, create_time) values (#{name,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP})")
    int insert(RoncooUser record);

    @Select(value = "select id, name, create_time from roncoo_user where id = #{id,jdbcType=INTEGER}")
    @Results(value = { @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP) })
    RoncooUser selectByPrimaryKey(Integer id);
}
```

- `@Mapper` 让 MyBatis-Spring-Boot 为接口生成实现并放进容器，调用方 `@Autowired RoncooUserMapper` 即可。
- `#{name}` 从参数对象 `RoncooUser` 取 `name` 属性，是预编译参数。`jdbcType` 在值为 null 时告诉驱动用哪种 SQL 类型。
- 查询列 `create_time` 与属性 `createTime` 不一致，所以用 `@Results` / `@Result` 写明映射。`id`、`name` 名字能对上，可以不写。
- 启动类只有 `@SpringBootApplication`，没有 `@MapperScan`。接口上的 `@Mapper` 已经足够。数据源仍是 `application.properties` 里的 MySQL URL。

工程里还有一个 `com.roncoo.example.SpringBootDemo281Application`，与上面这个启动类重复。运行时只启动 `com.roncoo.education` 这个，Mapper 才在扫描范围内。

## 关键源码路径

按下面路径在 IDE 中打开对照（相对各 demo 工程根目录）：

**Java**

- `src/main/java/com/roncoo/education/SpringBootDemo281Application.java`
- `src/main/java/com/roncoo/education/bean/RoncooUser.java`
- `src/main/java/com/roncoo/education/mapper/RoncooUserMapper.java`
- `src/main/java/com/roncoo/example/SpringBootDemo281Application.java`

**配置/模板**

- `src/main/resources/application.properties`
## 学习路径

- 上一模块：[`spring-boot-demo-27-1`](./spring-boot-demo-27-1.md)
- 下一模块：[`spring-boot-demo-29-1`](./spring-boot-demo-29-1.md)
