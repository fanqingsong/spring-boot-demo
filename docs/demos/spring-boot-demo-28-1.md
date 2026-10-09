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
