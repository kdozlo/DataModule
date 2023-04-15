package yhdatabase.datamodule.service;

import lombok.Data;
import org.json.JSONObject;

import java.util.HashMap;

@Data
public class SqlResultDto {
    private Long flowId;

    // {"column" : [a, b, c], "values" : [{"column1" : "a", "column2" : "b"}, {...}, ...]}  ...
    private JSONObject results;

    public SqlResultDto(Long flowId, JSONObject results) {
        this.flowId = flowId;
        this.results = results;
    }
}
