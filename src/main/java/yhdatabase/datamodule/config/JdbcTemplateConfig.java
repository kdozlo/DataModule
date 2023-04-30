package yhdatabase.datamodule.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import yhdatabase.datamodule.repository.OnlineTransIsolRepository;
import yhdatabase.datamodule.repository.ProgMstRepository;
import yhdatabase.datamodule.repository.ProgWorkFlowMngRepository;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class JdbcTemplateConfig {
    private final DataSource dataSource;

    @Bean
    public ProgWorkFlowMngRepository progWorkFlowMngRepository() {
            return new ProgWorkFlowMngRepository(dataSource);
        }

    @Bean
    public ProgMstRepository progMstRepository() {
        return new ProgMstRepository(dataSource);
    }

    @Bean
    public OnlineTransIsolRepository onlineTransIsolRepository() {
        return new OnlineTransIsolRepository(dataSource);
    }

}
