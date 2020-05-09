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
#### 物种基础的操作模式方法

stringRedisTemplate.opsForValue();　　//操作字符串

stringRedisTemplate.opsForHash();　　 //操作hash

stringRedisTemplate.opsForList();　　 //操作list

stringRedisTemplate.opsForSet();　　  //操作set

stringRedisTemplate.opsForZSet();　 　//操作有序set

获取数据库中值是String类型的所有数据
` ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();`

```
ops.set(key,value); //向redis中插入数据。因为这个没有设置过期时间所以是永久存储的。

ops.set(key,value,time,timeUtil); //向redis中插入数据。第三个参数是一个long型的时间。最后一个参数是时间的单位。比如我上面demo中就是设置的50秒过期。

ops.get(key);//获取redis中指定key 的value值。

注意下上面的是针对字符串类型value的操作。如果是别的值类型操作是有一点点不同的，例如set：

stringRedisTemplate.opsForSet().add("keySet", "1","2","3"); //向指定key中存放set集合

stringRedisTemplate.opsForSet().isMember("keySet", "1"); //根据key查看集合中是否存在指定数据

stringRedisTemplate.opsForSet().members("keySet"); //根据key获取set集合

stringRedisTemplate还有一些别的方法：

stringRedisTemplate.expire(key,1000 , TimeUnit.MILLISECONDS); //设置过期时间

stringRedisTemplate.hasKey("isHas"); //检查key是否存在，返回boolean值

stringRedisTemplate.delete(key); //根据key删除记录

stringRedisTemplate.getExpire(key)； //根据key获取过期时间

stringRedisTemplate.getExpire(key,TimeUnit.SECONDS); //根据key获取过期时间并换算成指定
```


### redis做接口缓存
#### 添加依赖：               
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
```

#### 序列化
往Redis里做的缓存的单位并不是单纯的一个对象，或者一个字符串这么简单，而是缓存了某个接口的全部返回内容。                          
例如我有一个获取商品列表的接口，那么我的 Redis 缓存的就是这个接口返回的数据，所以我们是对接口进行操作的
但是我们要先把返回的内容进行序列化，必须是可以序列化的对象才能被缓存到 Redis 里
```java
@Data
public class ProductInfoVO implements Serializable {
    private static final long serialVersionUID = 4754730660459228000L;
    @JsonProperty("id")
    private String productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("price")
    private BigDecimal productPrice;

    @JsonProperty("description")
    private String productDescription;

    @JsonProperty("icon")
    private String productIcon;
}
```

#### 接口缓存
接口上加 cache 的注解，他就会自动地把这个接口的返回结果缓存到 Redis 里，下次再访问这个接口的时候它就会先去 Redis 里查一下有没有要的数据，有的话就不再进入这个接口，而是直接从 Redis 里获取那些数据，提高了效率，也节约了资源
```java
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    @Cacheable(cacheNames = "prodcut", key = "123")
    public ResultVO list() {
        // 查询所有上架的产品
        List<ProductInfo> productInfoList = productService.findUpAll();

        // 获取商品类目
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(ProductInfo::getCategoryType)
                .collect(Collectors.toList());

        // 拿到商品类目对应的商品种类信息
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory : productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo: productInfoList) {
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }
        return ResultVOUtil.success(productVOList);
    }
}
```

此外我们还可以加一个过虑，就是当这个接口返回正确时才缓存，不正确时不缓存，看一下我们上面的 ResultVO 类，有一个属性叫错误码，返回正确时为 0，那么我们可以使用 unless 这个注解项，如下
```java
@Cacheable(cacheNames = "product", key = "123",unless = "#result.getCode() != 0")
public ResultVO list(){
    ...
}
```
里面的 #result 表示返回的对象，那么这个注解的意思时  它会进行缓存除非状态码不为 0 ，所以只有状态码为 0 时才会缓存


#### 更新缓存的问题
`@CacheEvict`  这个注解会在方法执行后去清除注解里指定 key 的缓存
`@CachePut`   这个注解跟 Cacheable 一样会把返回的内容做缓存，但是不一样的是，它不会在方法执行前去判断是否执行方法，而是永远都执行方法，然后更新掉


注意 key 要对应好，我们上面的注解给的 key 是 123，那么我们要清除缓存时这个 key 要一样
```
    @CacheEvict(key = "123")
    public ModelAndView save(@Valid ProductForm form,
                             BindingResult bindingResult,
                             Map<String,Object> map){
        if(bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("common/error",map);
        }
        ProductInfo productInfo = new ProductInfo();
        try {
            // 如果productId 为空是新增
            if(!StringUtils.isEmpty(form.getProductId())){
                productInfo = productInfoService.findOne(form.getProductId());
            }else{
                form.setProductId(KeyUtil.genUniqueKey());
            }
            BeanUtils.copyProperties(form,productInfo);
            productInfoService.save(productInfo);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("common/error",map);
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }
```

再举个 `CachePut` 的例子
```
    @Override
    @Cacheable(key = "111")
    public ProductInfo findOne(String productId) {
        System.out.println("【进入查询数据库商品信息】");
        return repository.findOne(productId);
    }
 
    @Override
    @CachePut(key = "111")
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }
```




### 参考文档
- [Redis，StringRedisTemplate使用讲解](https://www.jianshu.com/p/8f03af4717e7)
- [使用StringRedisTemplate进行redis连接操作](https://blog.csdn.net/baomw/article/details/89186501)
- [RedisTemplate和StringRedisTemplate的区别](https://blog.csdn.net/yifanSJ/article/details/79513179)
- [如何使用StringRedisTemplate操作Redis详解](https://segmentfault.com/a/1190000019952021)
- [Java 使用Redis做数据缓存](https://blog.csdn.net/linzhiqqq/article/details/81487477)