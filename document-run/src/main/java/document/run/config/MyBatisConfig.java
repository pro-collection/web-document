package document.run.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("document.run.mbg.mapper")
public class MyBatisConfig {
}
