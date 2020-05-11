## SpringSecurity和JWT 

### 整合SpringSecurity及JWT
添加依赖
```xml
<!--SpringSecurity依赖配置-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<!--Hutool Java工具包-->
<dependency>
    <groupId>cn.hutool</groupId>
    <artifactId>hutool-all</artifactId>
    <version>4.5.7</version>
</dependency>
<!--JWT(Json Web Token)登录支持-->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.9.0</version>
</dependency>
```

- src/main/java/document/run/common/utils/JwtTokenUtil.java
- src/main/java/document/run/component/RestfulAccessDeniedHandler.java
- src/main/java/document/run/component/RestAuthenticationEntryPoint.java
- src/main/java/document/run/component/JwtAuthenticationTokenFilter.java
- src/main/java/document/run/config/SecurityConfig.java
- src/main/java/document/run/dto/AdminUserDetails.java
- src/main/java/document/run/dto/UmsAdminLoginParam.java
- src/main/java/document/run/service/UmsAdminService.java
- src/main/java/document/run/service/impl/UmsAdminServiceImpl.java
- src/main/java/document/run/dao/UmsAdminRoleRelationDao.java
- src/main/resources/mapper/UmsAdminRoleRelationDao.xml
- src/main/java/document/run/config/Swagger2Config.java
- src/main/java/document/run/controller
