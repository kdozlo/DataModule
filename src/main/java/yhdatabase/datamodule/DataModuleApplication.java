package yhdatabase.datamodule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import yhdatabase.datamodule.config.JdbcTemplateConfig;

@Slf4j
@Import(JdbcTemplateConfig.class)
@SpringBootApplication
public class DataModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataModuleApplication.class, args);
	}

}
