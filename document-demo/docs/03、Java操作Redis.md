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

# 自定义redis key
redis:
  key:
    prefix:
      authCode: "portal:authCode:"
    expire:
      authCode: 120 # 验证码超期时间
```

### 使用实例
service 层：                  
```java
public interface UmsMemberService {
    /* 生成验证码 */
    CommonResult generateAuthCode(String phone);

    /* 判断验证码和手机是否匹配 */
    CommonResult verifyAuthCode(String phone, String authCode);
}
```

service impl:                   
```java
@Service
public class UmsMemberServiceImpl implements UmsMemberService {
    @Autowired
    private RedisService redisService;

    @Value("${redis.key.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;
    @Value("${redis.key.expire.authCode}")
    private Long REDIS_KEY_EXPIRE_AUTH_CODE;

    @Override
    public CommonResult generateAuthCode(String phone) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }

        // 验证码绑定手机， 并且存储到redis
        redisService.set(REDIS_KEY_PREFIX_AUTH_CODE + phone, sb.toString());
        redisService.expire(REDIS_KEY_PREFIX_AUTH_CODE + phone, REDIS_KEY_EXPIRE_AUTH_CODE);

        return CommonResult.success(sb.toString(), "获取验证码成功");
    }

    @Override
    /* 验证验证码 */
    public CommonResult verifyAuthCode(String phone, String authCode) {
        if (StringUtil.isNullOrEmpty(authCode)) {
            return CommonResult.failed("请输入验证码");
        }

        String realAuthCode = redisService.get(REDIS_KEY_PREFIX_AUTH_CODE + phone);
        boolean result = authCode.equals(realAuthCode);
        if (result) {
            return CommonResult.success(null, "验证码校验成功");
        } else {
            return CommonResult.failed("验证码不正确");
        }
    }
}
```


### 常用api总结


### redis做接口缓存



### 参考文档
- [Redis，StringRedisTemplate使用讲解](https://www.jianshu.com/p/8f03af4717e7)
- [使用StringRedisTemplate进行redis连接操作](https://blog.csdn.net/baomw/article/details/89186501)
- [RedisTemplate和StringRedisTemplate的区别](https://blog.csdn.net/yifanSJ/article/details/79513179)
- [如何使用StringRedisTemplate操作Redis详解](https://segmentfault.com/a/1190000019952021)