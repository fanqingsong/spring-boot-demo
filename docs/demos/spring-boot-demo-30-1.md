# spring-boot-demo-30-1：Spring Boot 集成 Swagger

> 工程目录：[`spring-boot-demo-30-1`](../../spring-boot-demo-30-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **Spring Boot 集成 Swagger** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

本节每个知识点从 **为何出现**、**解决什么问题**、**若没有会怎样** 三方面说明，便于理解「为什么要学这一项」，而不只是记名词。

- **Springfox Swagger2**
  - **为何出现**：接口多了后 Word/口头同步文档永远过期；需要由代码或注解自动生成可交互文档。
  - **解决什么问题**：`@EnableSwagger2` + Docket 扫描 Controller，生成 Swagger 2 规范与 UI。
  - **若没有会怎样**：前后端字段不一致联调慢；新人不知道有哪些 API，重复造轮子。

- **扫描 `/api/**`**
  - **为何出现**：全站 Controller 都进文档会暴露内部、Actuator 路径，文档噪音大。
  - **解决什么问题**：Docket 用 regex 限制只文档化对外 API 前缀，边界清晰。
  - **若没有会怎样**：Swagger 列出 /error、内部工具接口，误导调用方或扩大攻击面认知。

- **在线文档与调试**
  - **为何出现**：Postman 集合也要人维护；Swagger UI 随代码更新，可浏览器试调。
  - **解决什么问题**：启动访问 swagger-ui.html 看参数模型、直接发请求，缩短联调周期。
  - **若没有会怎样**：每次改接口都要手工通知前端参数变更，漏字段导致线上 400。

## 代码实战（对照源码）

### 1. `Swagger2Configuration`

定义 apiInfo、分组、路径 regex。

### 2. `ApiController`

Swagger 注解描述接口（若有）。

## 运行与验证

- 启动后浏览器打开 swagger-ui（一般为 /swagger-ui.html）。

## 动手练习

- 为新接口补充 ApiOperation 描述。


## 关键代码说明

Swagger 扫描 Controller，生成可在浏览器里试调用的 API 文档。

```java
@Configuration
@EnableSwagger2
public class Swagger2Configuration {

    @Bean
    public Docket accessToken() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("api")
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.roncoo.education.controller"))
            .paths(regex("/api/.*"))
            .build()
            .apiInfo(apiInfo());
    }
}
```

- `@EnableSwagger2` 打开文档端点，默认页面是 `/swagger-ui.html`。
- `basePackage` 只扫描 `com.roncoo.education.controller`。
- `paths(regex("/api/.*"))` 再滤一遍 URL，只有 `/api/` 开头的接口进入文档。

接口上的注解决定文档里怎么展示：

```java
@ApiOperation(value = "查找", notes = "根据用户ID查找用户")
@RequestMapping(value = "/select", method = RequestMethod.GET)
public RoncooUser get(@RequestParam(defaultValue = "1") Integer id) {
    return roncooUserMappper.selectByPrimaryKey(id);
}

@ApiIgnore
@RequestMapping(value = "/delete", method = RequestMethod.GET)
public int delete(@RequestParam(defaultValue = "1") Integer id) {
    return roncooUserMappper.deleteByPrimaryKey(id);
}
```

`@ApiOperation` 的 `value` 是接口标题，`notes` 是补充说明。`@ApiIgnore` 让 `/api/delete` 不出现在文档中，即使路径匹配 `/api/.*`。

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
