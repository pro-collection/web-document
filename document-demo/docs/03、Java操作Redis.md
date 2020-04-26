## Java操作Redis

### 配置
pom.xml
```xml
<!--redis依赖配置-->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

application.yml
```yaml
  redis:
    host: localhost # Redis服务器地址
    database: 0 # Redis数据库索引（默认为0）
    port: 6379 # Redis服务器连接端口
    password: # Redis服务器连接密码（默认为空）
    jedis:
      pool:
        max-active: 8 # 连接池最大连接数（使用负值表示没有限制）
        max-wait: -1ms # 连接池最大阻塞等待时间（使用负值表示没有限制）
        max-idle: 8 # 连接池中的最大空闲连接
        min-idle: 0 # 连接池中的最小空闲连接
    timeout: 3000ms # 连接超时时间（毫秒）
```



### 参考文档
- [Redis，StringRedisTemplate使用讲解](https://www.jianshu.com/p/8f03af4717e7)
- [使用StringRedisTemplate进行redis连接操作](https://blog.csdn.net/baomw/article/details/89186501)
- [RedisTemplate和StringRedisTemplate的区别](https://blog.csdn.net/yifanSJ/article/details/79513179)
- [如何使用StringRedisTemplate操作Redis详解](https://segmentfault.com/a/1190000019952021)