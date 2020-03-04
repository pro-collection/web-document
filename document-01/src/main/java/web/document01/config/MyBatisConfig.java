package web.document01.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
@MapperScan("web.document.mbg.mapper")
public class MyBatisConfig {
}
