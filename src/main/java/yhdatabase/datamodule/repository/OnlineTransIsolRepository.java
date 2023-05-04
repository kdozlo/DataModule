package yhdatabase.datamodule.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import yhdatabase.datamodule.domain.ProgWorkFlowMng;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j
public class OnlineTransIsolRepository {
    private final JdbcTemplate template;

    public OnlineTransIsolRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    public List<Map<String, Object>> findSQLResult(Optional<ProgWorkFlowMng> progWorkFlowMng) {
        String sql = progWorkFlowMng.get().findSql();

        List<Map<String, Object>> result = template.queryForList(sql);

        return result;
    }
}
