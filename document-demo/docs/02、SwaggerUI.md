## SwaggerUI

### 依赖
```xml
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.9.2</version>
</dependency>
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.9.2</version>
</dependency>
```

### 在Java中的配置
简单的配置参考这个地方就可以了
[Swagger2Config.java](../src/main/java/web/document/config/Swagger2Config.java)

### 常用api
Swagger使用的注解及其说明：

- @Api：用在类上，说明该类的作用。

- @ApiOperation：注解来给API增加方法说明。

- @ApiImplicitParams : 用在方法上包含一组参数说明。

- @ApiImplicitParam：用来注解来给方法入参增加说明。

- @ApiResponses：用于表示一组响应

- @ApiResponse：用在@ApiResponses中，一般用于表达一个错误的响应信息
    - code：数字，例如400
    - message：信息，例如"请求参数没填好"
    - response：抛出异常的类   

- @ApiModel：描述一个Model的信息（一般用在请求参数无法使用@ApiImplicitParam注解进行描述的时候）
     - @ApiModelProperty：描述一个model的属性
    
@ApiImplicitParam的参数说明：
    - paramType：指定参数放在哪个地方: header、query、path、body、form
    - name：参数名
    - dataType：参数类型
    - required：参数是否必须传: true、false
    - value：说明参数的意思
    - defaultValue：参数的默认值
    
    
          

### 参考文档
- [Swagger UI初识](https://www.jianshu.com/p/5c1111d3b99f)
- [使用SwaggerUI生成接口文档（配示例）](https://www.jianshu.com/p/79a52e4977da?utm_campaign)