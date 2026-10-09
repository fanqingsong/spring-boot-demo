# spring-boot-demo-24-1：发送邮件 - 模板邮件与多账号轮询

> 工程目录：[`spring-boot-demo-24-1`](../../spring-boot-demo-24-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **发送邮件 - 模板邮件与多账号轮询** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

- **`spring-boot-starter-mail`**：自动配置 `JavaMailSender`，通过 SMTP 发送邮件，用于通知、验证码、报表等。
- **简单邮件与 MIME 邮件**：纯文本/quick 发送 vs 支持 HTML、附件、内嵌资源的 MIME 结构，满足不同展示需求。
- **FreeMarker 模板 + 多 SMTP 账号**：用模板渲染 HTML 正文；多账号实现轮询或 failover，避免单邮箱限流导致发送失败。

## 代码实战（对照源码）

### 1. `RoncooJavaMailComponent`

组装邮件内容并发送。

### 2. `RoncooJavaMailSenderImpl`

多账号选择策略。

## 运行与验证

- 配置真实 SMTP（或测试邮箱）；调用发送接口收信。

## 动手练习

- HTML 模板邮件嵌入图片（cid）。


## 关键代码说明

发信分两步：FreeMarker 渲染正文，自定义 `JavaMailSender` 在多个账号之间轮询。

### 渲染并发送

```java
public void sendMail(String email) {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("email", email);
    String text = getTextByTemplate("mail/roncoo.ftl", map);
    send(email, text);
}
```

`getTextByTemplate` 调用 `FreeMarkerTemplateUtils.processTemplateIntoString`。模板 `templates/mail/roncoo.ftl` 里的 `${email}` 被换成收件人地址，得到一段 HTML。

```java
MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
helper.setFrom(from);
helper.setTo(email);
helper.setSubject("测试邮件");
helper.setText(text, true);
javaMailSender.send(message);
```

`setText(text, true)` 的 `true` 表示正文是 HTML。

### 多账号轮询

`spring.mail.username` 和 `spring.mail.password` 按逗号拆成列表（配置里是多组邮箱和密码，文档不抄录明文）。构造方法把它们放进 `usernameList`、`passwordList`。

```java
@Override
protected void doSend(MimeMessage[] mimeMessage, Object[] object) throws MailException {
    super.setUsername(usernameList.get(currentMailId));
    super.setPassword(passwordList.get(currentMailId));
    super.setHost(this.properties.getHost());
    super.doSend(mimeMessage, object);
    currentMailId = (currentMailId + 1) % usernameList.size();
}
```

每次真正 `send` 时才选定当前下标的账号，发完后下标加一并对账号数取模。下一次调用用下一个账号。用户名和密码列表长度必须一致，否则会按下标取错密码。

## 关键源码路径

按下面路径在 IDE 中打开对照（相对各 demo 工程根目录）：

**Java**

- `src/main/java/com/roncoo/example/SpringBootDemo241Application.java`
- `src/main/java/com/roncoo/example/bean/RoncooUser.java`
- `src/main/java/com/roncoo/example/bean/RoncooUserLog.java`
- `src/main/java/com/roncoo/example/cache/RoncooUserLogCache.java`
- `src/main/java/com/roncoo/example/cache/impl/RoncooUserLogCacheImpl.java`
- `src/main/java/com/roncoo/example/component/RoncooJavaMailComponent.java`
- `src/main/java/com/roncoo/example/component/RoncooMongodbComponent.java`
- `src/main/java/com/roncoo/example/component/RoncooRedisComponent.java`
- `src/main/java/com/roncoo/example/controller/ApiController.java`
- `src/main/java/com/roncoo/example/controller/FileController.java`
- `src/main/java/com/roncoo/example/controller/RestRoncooController.java`
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
- `src/main/java/com/roncoo/example/util/configuration/RoncooJavaMailSenderImpl.java`
- `src/main/java/com/roncoo/example/util/filter/CustomFilter.java`
- `... 共 27 个文件`

**配置/模板**

- `src/main/resources/application-dev.properties`
- `src/main/resources/application-prod.properties`
- `src/main/resources/application-test.properties`
- `src/main/resources/application.properties`
- `src/main/resources/config/ehcache.xml`
- `src/main/resources/logback-roncoo.xml`
- `src/main/resources/public/error/404.html`
- `src/main/resources/templates/error/500.ftl`
- `src/main/resources/templates/error/5xx.ftl`
- `src/main/resources/templates/error/error.ftl`
- `src/main/resources/templates/index.ftl`
- `src/main/resources/templates/mail/roncoo.ftl`
## 学习路径

- 上一模块：[`spring-boot-demo-23-1`](./spring-boot-demo-23-1.md)
- 下一模块：[`spring-boot-demo-25-1`](./spring-boot-demo-25-1.md)
