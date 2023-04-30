package yhdatabase.datamodule.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import yhdatabase.datamodule.domain.OnlineTransIsol;

import javax.sql.DataSource;
import java.util.List;


@Slf4j
@Repository
@RequiredArgsConstructor
public class OnlineTransIsolRepository {
    private final JdbcTemplate template;

    public OnlineTransIsolRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    public List<OnlineTransIsol> findSQLResult() {
        return null;
    }
}
