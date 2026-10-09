# spring-boot-demo-31-1：生产部署 - 注意事项与脚本

> 工程目录：[`spring-boot-demo-31-1`](../../spring-boot-demo-31-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **生产部署 - 注意事项与脚本** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- 打包：spring-boot-maven-plugin 可执行 jar
- Druid 监控、Swagger 等生产环境开关
- 与 30-1 技术栈延续，侧重部署与运维

## 代码实战（对照源码）

### 1. `DruidStatViewServlet`

生产环境限制 Druid 控制台访问 IP/密码。

### 2. 配置与脚本

参考 README/视频：启动脚本、JVM 参数、profile=prod。

## 运行与验证

- mvn clean package -DskipTests && java -jar target/*.jar --spring.profiles.active=prod

## 动手练习

- 编写 systemd 或 docker 方式托管 jar。


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
