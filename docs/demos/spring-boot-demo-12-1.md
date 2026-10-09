# spring-boot-demo-12-1：WEB 应用开发 - 文件上传

> 工程目录：[`spring-boot-demo-12-1`](../../spring-boot-demo-12-1) · Spring Boot 1.4.x

## 学习目标

学完本模块，你能说明 **WEB 应用开发 - 文件上传** 在 Spring Boot 中的用法，并对照代码完成一次本地运行验证。

## 核心知识点

本节每个知识点从 **为何出现**、**解决什么问题**、**若没有会怎样** 三方面说明，便于理解「为什么要学这一项」，而不只是记名词。

- **`MultipartFile`**
  - **为何出现**：HTTP multipart 上传是表单传文件的标准；Servlet 3 以前解析 multipart 非常繁琐。
  - **解决什么问题**：Spring 封装流、文件名、大小，Controller 一个参数接住文件域，避免低级 IO 样板代码。
  - **若没有会怎样**：手写 `Part`/`CommonsMultipartFile` 解析，易漏临时文件清理、大小校验和编码问题。

- **`spring.http.multipart.*`**
  - **为何出现**：无限制上传会导致 DoS（超大文件占满磁盘/内存），运维事故常见。
  - **解决什么问题**：配置 max-file-size、max-request-size、location，超限快速失败保护服务器。
  - **若没有会怎样**：默认或过大限制被利用，磁盘满导致整机服务不可用。

- **`transferTo(File)`**
  - **为何出现**：业务通常要把上传持久化到磁盘或对象存储，而不是一直占着内存流。
  - **解决什么问题**：一次性写入目标路径；本 demo UUID 重名避免覆盖与路径遍历攻击（还需校验后缀等）。
  - **若没有会怎样**：只读内存不落盘，或原始文件名覆盖同名文件，造成数据丢失或安全漏洞。

## 代码实战（对照源码）

### 1. `FileController`

`@RequestParam("roncooFile") MultipartFile file`，UUID 重命名防冲突，保存到配置目录（示例为 Windows 路径，Linux 需修改）。

## 运行与验证

- 用表单或 Postman multipart 上传文件到 `/file/upload`。
- 修改 application 中 multipart 限制，测试超大文件拒绝。

## 动手练习

- 限制允许的后缀名；上传失败返回 JSON 错误码。


## 关键代码说明

上传接口把 multipart 文件落到本地磁盘。

```java
@RequestMapping(value = "upload")
@ResponseBody
public String upload(@RequestParam("roncooFile") MultipartFile file) {
    if (file.isEmpty()) {
        return "文件为空";
    }
    String fileName = file.getOriginalFilename();
    String suffixName = fileName.substring(fileName.lastIndexOf("."));
    String filePath = "d:/roncoo/education/";
    fileName = UUID.randomUUID() + suffixName;
    File dest = new File(filePath + fileName);
    if (!dest.getParentFile().exists()) {
        dest.getParentFile().mkdirs();
    }
    file.transferTo(dest);
    return "上传成功";
}
```

- 类是 `@Controller`，方法上加 `@ResponseBody`，返回的 `"上传成功"` 是响应正文，不是视图名。
- `@RequestParam("roncooFile")` 的名字必须和表单字段 `name="roncooFile"` 一致，类型是 `MultipartFile`。
- `getOriginalFilename()` 只用来取后缀。真正保存的文件名改成 UUID，避免中文名和重名覆盖。
- `transferTo` 把上传的临时文件写到 `dest`。目录不存在时先 `mkdirs()`。
- 路径写死为 `d:/roncoo/education/`。在 Linux 上应改成实际可写目录，否则 `transferTo` 抛 `IOException`，方法返回 `"上传失败"`。

## 关键源码路径

按下面路径在 IDE 中打开对照（相对各 demo 工程根目录）：

**Java**

- `src/main/java/com/roncoo/example/SpringBootDemo121Application.java`
- `src/main/java/com/roncoo/example/controller/ApiController.java`
- `src/main/java/com/roncoo/example/controller/FileController.java`
- `src/main/java/com/roncoo/example/controller/WebController.java`
- `src/main/java/com/roncoo/example/handler/BizExcepiton.java`
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

- 上一模块：[`spring-boot-demo-11-1`](./spring-boot-demo-11-1.md)
- 下一模块：[`spring-boot-demo-13-1`](./spring-boot-demo-13-1.md)
