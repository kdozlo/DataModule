package yhdatabase.datamodule.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class OutPutTableRepository {

    private final NamedParameterJdbcTemplate template;

    public OutPutTableRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    public int insertResult(List<Map<String, Object>> result,
                            String tableNm, Map<String, String[]> condList) {
        int resultNum = 0;
        String sql = generateInsertSql(tableNm, condList.keySet());

        for (Map<String, Object> row : result) {
            MapSqlParameterSource parameterSource = setParameters(condList, row);
            resultNum += template.update(sql, parameterSource);
        }

        return resultNum;
    }

    public int updateResult(List<Map<String, Object>> result,
                            String tableNm, Map<String, String[]> condList, List<String> pk) {
        int resultNum = 0;
        String sql = generateUpdateSql(tableNm, condList.keySet(), pk.get(1));

        for (Map<String, Object> row : result) {
            MapSqlParameterSource parameterSource = setParameters(condList, row);
            parameterSource.addValue(pk.get(1), row.get(pk.get(0)));
            resultNum += template.update(sql, parameterSource);
        }

        return resultNum;
    }

    public int deleteResult(List<Map<String, Object>> result, String tableNm, List<String> pk) {
        int resultNum = 0;
        String sql = generateDeleteSql(tableNm, pk.get(1));

        for (Map<String, Object> row : result) {
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue(pk.get(1), row.get(pk.get(0)));
            resultNum += template.update(sql, parameterSource);
        }

        return resultNum;
    }

    private String generateInsertSql(String tableNm, Set<String> condListKey) {
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(tableNm).append(" (");
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for (String column : condListKey) {
            columns.append(column).append(", ");
            values.append(":").append(column).append(", ");
        }

        // 마지막 쉼표 제거
        columns.delete(columns.length() - 2, columns.length());
        values.delete(values.length() - 2, values.length());

        sql.append(columns).append(") VALUES (").append(values).append(")");

        return sql.toString();
    }

    private String generateUpdateSql(String tableNm, Set<String> condListKey, String pkColumn) {
        StringBuilder sql = new StringBuilder("UPDATE ");
        sql.append(tableNm).append(" SET ");
        StringBuilder setClause = new StringBuilder();

        for (String column : condListKey) {
            if (column.equals(pkColumn)) {
                continue;
            }
            setClause.append(column).append(" = :").append(column).append(", ");
        }

        // 마지막 쉼표 제거
        setClause.delete(setClause.length() - 2, setClause.length());

        sql.append(setClause).append(" WHERE ").append(pkColumn).append(" = :").append(pkColumn);

        return sql.toString();
    }

    private String generateDeleteSql(String tableNm, String pkColumn) {
        StringBuilder sql = new StringBuilder("DELETE FROM ");
        sql.append(tableNm).append(" WHERE ").append(pkColumn).append(" = :").append(pkColumn);
        return sql.toString();
    }

    private MapSqlParameterSource setParameters(Map<String, String[]> condList, Map<String, Object> row) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        for (Map.Entry<String, String[]> entry : condList.entrySet()) {
            String column = entry.getKey();
            String[] condArray = entry.getValue();
            String defaultValue = condArray[0];
            String field = condArray[1];

            Object value = (field.equals("")) ? defaultValue : row.get(field);
            parameterSource.addValue(column, value);
        }

        return parameterSource;
    }
}