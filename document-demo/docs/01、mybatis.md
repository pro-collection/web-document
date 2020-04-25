## 添加mybatis

### 配置

application.yml:                
```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mall?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
    username: root
    password: root

mybatis:
  mapper-locations:
    - classpath:mapper/*.xml
    - classpath*:com/**/mapper/*.xml
```

[generatorConfig.xml](../src/main/resources/generatorConfig.xml)                                        
然后执行这个文件就可以了[Generator.java](../src/main/java/web/document/mbg/Generator.java)


