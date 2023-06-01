package yhdatabase.datamodule.repository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import yhdatabase.datamodule.domain.ProgWorkFlowMng;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j
public class DataProcessRepository {
    private final NamedParameterJdbcTemplate template;

    public DataProcessRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    //select 노드의 sql문을 통해 나오는 데이터들
    public List<Map<String, Object>> findSQLResult(Optional<ProgWorkFlowMng> progWorkFlowMng) {
        String sql = progWorkFlowMng.get().findSql();
        List<Map<String, Object>> result = template.getJdbcTemplate().queryForList(sql);

        return result;
    }


}
