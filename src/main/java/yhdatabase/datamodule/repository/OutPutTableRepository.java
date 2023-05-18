package yhdatabase.datamodule.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class OutPutTableRepository {
    private final OutPutTableMapper outPutTableMapper;

    public String createTable(String sql) {
        Map<String, String> map = new HashMap<>();
        map.put("sql", sql);
        outPutTableMapper.createTable(map);

        return sql;
    }
}
