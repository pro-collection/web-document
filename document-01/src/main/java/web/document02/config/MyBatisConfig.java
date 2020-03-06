package web.document02.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
@MapperScan("web.document01.mbg.mapper")
public class MyBatisConfig {
}
