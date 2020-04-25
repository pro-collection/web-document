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
    
### 使用实例
作用在controller上面：                
```java
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Api(tags="订单模块") // Swagger-UI 描述在类上面，可以做分组。
public class OrderController {

        @RequestMapping(method = RequestMethod.GET, value = "/order/orderInfo/{orderId:.+}")
        @ResponseBody
        @ApiOperation(value = "获取订单") // 让Swagger-UI解析这个接口。
        public OrderInfo getOrderInfo(String orderId) {
                return new OrderInfo();
        }
        
        @RequestMapping(method = RequestMethod.DELETE, value = "/order/delOrder.do")
        @ResponseBody
        @ApiOperation(value = "删除订单")
        public BaseResponse delOrder(String orderId) {
                return new BaseResponse();
        }
        
        @RequestMapping(method = RequestMethod.POST, value = "/order/queryOrderList.do")
        @ResponseBody
        @ApiOperation(value = "查询订单列表")
        public QueryOrderListResponse getOrderInfo(@RequestBody QueryOrderListRequest req) {
                return new QueryOrderListResponse();
        }
}
```

作用在DTO上：                        
```java
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "订单实体")
public class OrderInfo {

        @ApiModelProperty(value = "订单号", example = "201701011234", notes = "订单的编号")
        private String orderNo;
        
        @ApiModelProperty("创建时间")
        private Date createTime;
        
        @ApiModelProperty("中介公司编码")
        private String agencyCode;

        @ApiModelProperty("中介公司名称")
        private String agencyName;

        @ApiModelProperty("门店ID")
        private String storeId;

        @ApiModelProperty("门店名称")
        private String storeName;

        public String getOrderNo() {
                return orderNo;
        }

        public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
        }

        public Date getCreateTime() {
                return createTime;
        }

        public void setCreateTime(Date createTime) {
                this.createTime = createTime;
        }

        public String getAgencyCode() {
                return agencyCode;
        }

        public void setAgencyCode(String agencyCode) {
                this.agencyCode = agencyCode;
        }

        public String getAgencyName() {
                return agencyName;
        }

        public void setAgencyName(String agencyName) {
                this.agencyName = agencyName;
        }

        public String getStoreId() {
                return storeId;
        }

        public void setStoreId(String storeId) {
                this.storeId = storeId;
        }

        public String getStoreName() {
                return storeName;
        }

        public void setStoreName(String storeName) {
                this.storeName = storeName;
        }

}
```


### 本文档中的示例
- [PmsBrandController.java](../src/main/java/web/document/controller/PmsBrandController.java)
- [PmsBrand.java](../src/main/java/web/document/mbg/model/PmsBrand.java)
          

### 参考文档
- [Swagger UI初识](https://www.jianshu.com/p/5c1111d3b99f)
- [使用SwaggerUI生成接口文档（配示例）](https://www.jianshu.com/p/79a52e4977da?utm_campaign)