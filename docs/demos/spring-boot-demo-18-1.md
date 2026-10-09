# spring-boot-demo-18-1：使用 NoSQL 数据库 - MongoDB

> 工程目录：[`spring-boot-demo-18-1`](../../spring-boot-demo-18-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **使用 NoSQL 数据库 - MongoDB** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

本节每个知识点从 **为何出现**、**解决什么问题**、**若没有会怎样** 三方面说明，便于理解「为什么要学这一项」，而不只是记名词。

- **`spring-boot-starter-data-mongodb`**
  - **为何出现**：固定 schema 的关系库对日志、内容、嵌套 JSON 不友好；文档库随字段演化。
  - **解决什么问题**：连接 MongoDB，以 BSON 文档存储，适合非强关系、写多读少的场景。
  - **若没有会怎样**：硬塞 JSON 进 MySQL TEXT，查询索引差、schema 迁移痛苦。

- **MongoRepository / Template**
  - **为何出现**：简单 CRUD 想省代码；复杂聚合又要灵活 API，两种风格并存。
  - **解决什么问题**：Repository 快速 CRUD；Template 写自定义查询与聚合 pipeline。
  - **若没有会怎样**：只用 JDBC 思维写 Mongo，性能与模型都不对，全表扫 BSON。

- **`RoncooUserLogMongoDao`**
  - **为何出现**：同一「用户日志」业务可选 MySQL 表或 Mongo 集合，需要 demo 对比。
  - **解决什么问题**：演示日志类数据进 Mongo，理解何时选文档库。
  - **若没有会怎样**：技术选型单一，日志量一大把 MySQL 撑爆才想换库，迁移成本高。

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
