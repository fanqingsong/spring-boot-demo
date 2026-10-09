# spring-boot-demo-30-1：Spring Boot 集成 Swagger

> 工程目录：[`spring-boot-demo-30-1`](../../spring-boot-demo-30-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **Spring Boot 集成 Swagger** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- Springfox Swagger2：`@EnableSwagger2` + Docket
- 扫描 com.roncoo.education.controller 下 /api/**
- 在线 API 文档与调试

## 代码实战（对照源码）

### 1. `Swagger2Configuration`

定义 apiInfo、分组、路径 regex。

### 2. `ApiController`

Swagger 注解描述接口（若有）。

## 运行与验证

- 启动后浏览器打开 swagger-ui（一般为 /swagger-ui.html）。

## 动手练习

- 为新接口补充 ApiOperation 描述。


## 关键源码路径

按下面路径在 IDE 中打开对照（相对各 demo 工程根目录）：

**Java**

- `src/main/java/com/roncoo/education/SpringBootDemo301Application.java`
- `src/main/java/com/roncoo/education/bean/RoncooUser.java`
- `src/main/java/com/roncoo/education/bean/RoncooUserExample.java`
- `src/main/java/com/roncoo/education/bean/RoncooUserLog.java`
- `src/main/java/com/roncoo/education/bean/RoncooUserLogExample.java`
- `src/main/java/com/roncoo/education/controller/ApiController.java`
- `src/main/java/com/roncoo/education/mapper/RoncooUserLogMapper.java`
- `src/main/java/com/roncoo/education/mapper/RoncooUserMapper.java`
- `src/main/java/com/roncoo/education/util/configuration/DruidConfiguration.java`
- `src/main/java/com/roncoo/education/util/configuration/Swagger2Configuration.java`
- `src/main/java/com/roncoo/education/util/filter/DruidWebStatFilter.java`
- `src/main/java/com/roncoo/education/util/servlet/DruidStatViewServlet.java`

**配置/模板**

- `src/main/resources/application.properties`
- `src/main/resources/druid-bean.xml`
- `src/main/resources/mybatis/RoncooUserLogMapper.xml`
- `src/main/resources/mybatis/RoncooUserMapper.xml`
## 学习路径

- 上一模块：[`spring-boot-demo-29-1`](./spring-boot-demo-29-1.md)
- 下一模块：[`spring-boot-demo-31-1`](./spring-boot-demo-31-1.md)
