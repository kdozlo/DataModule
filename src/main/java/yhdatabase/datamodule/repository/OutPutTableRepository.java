package yhdatabase.datamodule.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import yhdatabase.datamodule.domain.ProgWorkFlowMng;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class OutPutTableRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate template;

    public OutPutTableRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    public int insertResult(List<Map<String, Object>> result,
                            String tableNm, Map<String, String[]> condList) {
        int resultNum = 0;
        Set<String> condListKey = condList.keySet();

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        for(Map<String, Object> row : result) {
            // 컬럼 목록 추출
            StringBuilder sql = new StringBuilder("INSERT INTO ");
            sql.append(tableNm + " (");
            StringBuilder columns = new StringBuilder();
            StringBuilder values = new StringBuilder();

            for(String s : condListKey) {
                String column = s;
                Object value;

                String field = condList.get(s)[1];
                if (field.equals("")) {
                    value = condList.get(s)[0];
                } else {
                    value = row.get(field);
                }

                columns.append(column).append(", ");
                values.append(":").append(column).append(", ");

                parameterSource.addValue(column, value);
            }

            // 마지막 쉼표 제거
            columns.delete(columns.length() - 2, columns.length());
            values.delete(values.length() - 2, values.length());

            sql.append(columns).append(") VALUES (").append(values).append(")");

            resultNum += template.update(sql.toString(), parameterSource);

        }



        return resultNum;
    }

    public String createTable(String sql) {
        jdbcTemplate.execute(sql);

        return sql;
    }

    public int insertResultToCreateTable(String tableNm, List<Map<String, Object>> result) {
        int resultNum = 0;

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        for(Map<String, Object> row : result) {
            // 컬럼 목록 추출
            StringBuilder sql = new StringBuilder("INSERT INTO ");
            sql.append("public." + tableNm + " (");
            StringBuilder columns = new StringBuilder();
            StringBuilder values = new StringBuilder();

            for (Map.Entry<String, Object> entry : row.entrySet()) {
                String column = entry.getKey();
                Object value = entry.getValue();

                columns.append(column).append(", ");
                values.append(":").append(column).append(", ");

                parameterSource.addValue(column, value);
            }

            // 마지막 쉼표 제거
            columns.delete(columns.length() - 2, columns.length());
            values.delete(values.length() - 2, values.length());

            sql.append(columns).append(") VALUES (").append(values).append(")");

            resultNum += template.update(sql.toString(), parameterSource);

        }

        return resultNum;
    }

}
