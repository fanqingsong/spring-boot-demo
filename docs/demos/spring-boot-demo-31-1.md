# spring-boot-demo-31-1：生产部署 - 注意事项与脚本

> 工程目录：[`spring-boot-demo-31-1`](../../spring-boot-demo-31-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **生产部署 - 注意事项与脚本** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

本节每个知识点从 **为何出现**、**解决什么问题**、**若没有会怎样** 三方面说明，便于理解「为什么要学这一项」，而不只是记名词。

- **`spring-boot-maven-plugin` 可执行 jar**
  - **为何出现**：传统 war 部署依赖容器版本；云原生更倾向单 artifact 内含依赖与启动类。
  - **解决什么问题**：repackage 成 fat jar，`java -jar` 一键启动，与开发环境一致。
  - **若没有会怎样**：漏 repackage 得到瘦 jar，线上 ClassNotFound；或仍依赖外置 Tomcat 增加运维复杂度。

- **生产环境开关**
  - **为何出现**：Druid/Swagger 等开发利器在生产是攻击面，Profile 必须关或加固。
  - **解决什么问题**：prod 关闭或 IP/强密码限制控制台与 swagger，符合安全基线。
  - **若没有会怎样**：生产 swagger 暴露全部 API、Druid 弱口令，渗透测试必过、合规必挂。

- **部署与运维侧重**
  - **为何出现**：能跑 demo ≠ 能上线；需要脚本、JVM 参数、profile 与监控配合。
  - **解决什么问题**：31 在 30 栈上强调 package、prod profile、启动脚本，完成交付闭环。
  - **若没有会怎样**：直接 mvn run 思维上线，无 heap/GC/profile 配置，一出流量就 OOM 或连错库。

## 代码实战（对照源码）

### 1. `DruidStatViewServlet`

生产环境限制 Druid 控制台访问 IP/密码。

### 2. 配置与脚本

参考 README/视频：启动脚本、JVM 参数、profile=prod。

## 运行与验证

- mvn clean package -DskipTests && java -jar target/*.jar --spring.profiles.active=prod

## 动手练习

- 编写 systemd 或 docker 方式托管 jar。


## 关键代码说明

本讲的应用代码与 30-1 相同（Druid、MyBatis、Swagger）。部署相关的是打包插件和启动时的 profile。

```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
</plugin>
```

`mvn package` 时这个插件会把依赖打进一个可执行 jar。启动命令：

```bash
java -jar target/spring-boot-demo-31-1-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

`--spring.profiles.active=prod` 覆盖配置文件里的默认 profile，从而选用生产数据源、日志级别等。不要把生产 profile 写死在源码里再重新打包。

Druid 监控 Servlet 仍是：

```java
@WebServlet(urlPatterns = { "/druid/*" }, initParams = {
    @WebInitParam(name = "loginUsername", value = "roncoo"),
    @WebInitParam(name = "loginPassword", value = "roncoo")
})
public class DruidStatViewServlet extends StatViewServlet {
}
```

生产环境应修改这里的账号，并限制谁能访问 `/druid/*`。Swagger 的 `/swagger-ui.html` 同样会暴露接口结构，生产环境应关闭 `@EnableSwagger2` 或加上访问控制。Actuator 若一并启用，敏感端点不要对公网匿名开放（见 27-1 的 `security.basic` 配置）。

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

- 上一模块：[`spring-boot-demo-30-1`](./spring-boot-demo-30-1.md)
- 下一模块：无（可回顾或做综合练习）
