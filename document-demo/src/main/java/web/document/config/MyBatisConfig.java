package web.document.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("web.document.mbg.mapper")
public class MyBatisConfig {
}
