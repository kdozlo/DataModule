package yhdatabase.datamodule.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import yhdatabase.datamodule.repository.OutPutTableMapper;
import yhdatabase.datamodule.repository.OutPutTableRepository;

@Configuration
@RequiredArgsConstructor
public class MyBatisConfig {

    private final OutPutTableMapper outPutTableMapper;

    @Bean
    public OutPutTableRepository outPutTableRepository() {
        return new OutPutTableRepository(outPutTableMapper);
    }
}
