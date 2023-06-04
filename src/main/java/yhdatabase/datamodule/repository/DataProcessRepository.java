package yhdatabase.datamodule.repository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import yhdatabase.datamodule.domain.ProgWorkFlowMng;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j
public class DataProcessRepository {
    private final NamedParameterJdbcTemplate template;
    private static final int BATCH_SIZE = 1000; // 일괄 처리할 배치 크기

    public DataProcessRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    // select 노드의 SQL 문을 통해 나오는 데이터들
    public List<Map<String, Object>> findSQLResult(Optional<ProgWorkFlowMng> progWorkFlowMng) {
        String sql = progWorkFlowMng.get().findSql();
        List<Map<String, Object>> result = new ArrayList<>();

        int offset = 0;
        while (true) {
            List<Map<String, Object>> batchData = fetchBatchData(sql, offset, BATCH_SIZE);
            if (batchData.isEmpty()) {
                break; // 가져올 데이터가 없으면 종료
            }

            result.addAll(batchData);
            offset += BATCH_SIZE;
        }

        return result;
    }

    private List<Map<String, Object>> fetchBatchData(String sql, int offset, int limit) {
        String query = sql + " LIMIT :limit OFFSET :offset";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("limit", limit)
                .addValue("offset", offset);

        return template.queryForList(query, params);
    }
}
