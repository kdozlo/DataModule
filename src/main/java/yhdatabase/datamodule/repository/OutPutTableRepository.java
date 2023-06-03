package yhdatabase.datamodule.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class OutPutTableRepository {

    private final NamedParameterJdbcTemplate template;
    private static final int BATCH_SIZE = 1000;

    public OutPutTableRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    public int insertResult(List<Map<String, Object>> result,
                            String tableNm, Map<String, String[]> condList) {
        int resultNum = 0;
        String sql = generateInsertSql(tableNm, condList.keySet());

        for (List<Map<String, Object>> batch : partitionList(result, BATCH_SIZE)) {
            List<MapSqlParameterSource> parameterSources = setBatchParameters(condList, batch);
            resultNum += template.batchUpdate(sql, parameterSources.toArray(new MapSqlParameterSource[0])).length;
        }

        return resultNum;
    }

    public int updateResult(List<Map<String, Object>> result,
                            String tableNm, Map<String, String[]> condList, List<String> pk) {
        int resultNum = 0;
        String sql = generateUpdateSql(tableNm, condList.keySet(), pk.get(1));

        for (List<Map<String, Object>> batch : partitionList(result, BATCH_SIZE)) {
            List<MapSqlParameterSource> parameterSources = setBatchParameters(condList, batch);
            for (MapSqlParameterSource parameterSource : parameterSources) {
                parameterSource.addValue(pk.get(1), parameterSource.getValue(pk.get(0)));
            }
            resultNum += template.batchUpdate(sql, parameterSources.toArray(new MapSqlParameterSource[0])).length;
        }

        return resultNum;
    }

    public int deleteResult(List<Map<String, Object>> result, String tableNm, List<String> pk) {
        int resultNum = 0;
        String sql = generateDeleteSql(tableNm, pk.get(1));

        for (List<Map<String, Object>> batch : partitionList(result, BATCH_SIZE)) {
            List<MapSqlParameterSource> parameterSources = setBatchParameters(batch, pk);
            resultNum += template.batchUpdate(sql, parameterSources.toArray(new MapSqlParameterSource[0])).length;
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

    private List<List<Map<String, Object>>> partitionList(List<Map<String, Object>> list, int batchSize) {
        List<List<Map<String, Object>>> partitions = new ArrayList<>();
        int size = list.size();

        for (int i = 0; i < size; i += batchSize) {
            int end = Math.min(size, i + batchSize);
            partitions.add(list.subList(i, end));
        }

        return partitions;
    }

    private List<MapSqlParameterSource> setBatchParameters(Map<String, String[]> condList, List<Map<String, Object>> batch) {
        List<MapSqlParameterSource> parameterSources = new ArrayList<>();

        for (Map<String, Object> row : batch) {
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();

            for (Map.Entry<String, String[]> entry : condList.entrySet()) {
                String column = entry.getKey();
                String[] condArray = entry.getValue();
                String defaultValue = condArray[0];
                String field = condArray[1];

                Object value = (field.equals("")) ? defaultValue : row.get(field);
                parameterSource.addValue(column, value);
            }

            parameterSources.add(parameterSource);
        }

        return parameterSources;
    }

    private List<MapSqlParameterSource> setBatchParameters(List<Map<String, Object>> batch, List<String> pk) {
        List<MapSqlParameterSource> parameterSources = new ArrayList<>();

        for (Map<String, Object> row : batch) {
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue(pk.get(1), row.get(pk.get(0)));
            parameterSources.add(parameterSource);
        }

        return parameterSources;
    }
}