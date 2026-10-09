# spring-boot-demo-18-1：使用 NoSQL 数据库 - MongoDB

> 工程目录：[`spring-boot-demo-18-1`](../../spring-boot-demo-18-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **使用 NoSQL 数据库 - MongoDB** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- **`spring-boot-starter-data-mongodb`**：连接 MongoDB，用文档（BSON）模型存数据，schema 灵活，适合日志、内容、非强关系结构。
- **MongoRepository / Template**：Repository 类似 JPA 的接口式 CRUD；Template 提供更自由的查询与聚合，本模块组件演示插入与查询文档。
- **`RoncooUserLogMongoDao`**：把用户操作日志存 MongoDB 集合，与关系型日志表对比，理解「同业务不同存储」的选型。

## 代码实战（对照源码）

### 1. `RoncooMongodbComponent`

演示插入与查询文档。

### 2. 配置

MongoDB 连接 URI 或 host/port。

## 运行与验证

- 启动 MongoDB 或使用嵌入式依赖（若配置）；写入后 mongo shell 查询。

## 动手练习

- 按 userName 条件查询文档列表 API。


## 关键代码说明

本讲同时出现两种 Mongo 用法。

### MongoTemplate：自己拼条件

```java
@Component
public class RoncooMongodbComponent {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void insert(RoncooUser roncooUser) {
        mongoTemplate.insert(roncooUser);
    }

    public RoncooUser selectById(int id) {
        Criteria criteria = Criteria.where("id").in(id);
        Query query = new Query(criteria);
        return mongoTemplate.findOne(query, RoncooUser.class);
    }
}
```

`insert` 把对象写成集合文档，集合名默认来自类名。查询时 `Criteria.where("id")` 对应文档字段，`findOne` 的第二个参数告诉模板把结果转成 `RoncooUser`。更新用 `Update.set("name", ...)` 只改指定字段，再 `updateMulti`。

### MongoRepository：方法名即查询

```java
public interface RoncooUserLogMongoDao extends MongoRepository<RoncooUserLog, Integer> {
    RoncooUserLog findByUserName(String string);
    RoncooUserLog findByUserNameAndUserIp(String string, String ip);
    Page<RoncooUserLog> findByUserName(String string, Pageable pageable);
}
```

和 14-1 的 JPA 接口同一套命名规则，只是基类换成 `MongoRepository`，背后是 MongoDB 而不是 SQL。

## 关键源码路径

按下面路径在 IDE 中打开对照（相对各 demo 工程根目录）：

**Java**

- `src/main/java/com/roncoo/example/SpringBootDemo181Application.java`
- `src/main/java/com/roncoo/example/bean/RoncooUser.java`
- `src/main/java/com/roncoo/example/bean/RoncooUserLog.java`
- `src/main/java/com/roncoo/example/component/RoncooMongodbComponent.java`
- `src/main/java/com/roncoo/example/component/RoncooRedisComponent.java`
- `src/main/java/com/roncoo/example/controller/ApiController.java`
- `src/main/java/com/roncoo/example/controller/FileController.java`
- `src/main/java/com/roncoo/example/controller/WebController.java`
- `src/main/java/com/roncoo/example/dao/RoncooUserDao.java`
- `src/main/java/com/roncoo/example/dao/RoncooUserLogDao.java`
- `src/main/java/com/roncoo/example/dao/RoncooUserLogMongoDao.java`
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

- 上一模块：[`spring-boot-demo-17-1`](./spring-boot-demo-17-1.md)
- 下一模块：[`spring-boot-demo-19-1`](./spring-boot-demo-19-1.md)
