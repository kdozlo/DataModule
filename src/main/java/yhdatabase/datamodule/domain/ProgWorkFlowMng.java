package yhdatabase.datamodule.domain;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ProgWorkFlowMng {
    private Long flowId;

    private Long progId;

    private int flowSeq;

    private String flowType;


    private JSONObject flowAttr;

    private String flowDesc;

    private LocalDateTime crtdDttm;

    private LocalDateTime updtdttm;

    private LocalDateTime dltDttm;

    public ProgWorkFlowMng(Long flowId, Long progId, int flowSeq, String flowType, JSONObject flowAttr,
                           String flowDesc, LocalDateTime crtdDttm, LocalDateTime updtdttm, LocalDateTime dltDttm) {

        this.flowId = flowId;
        this.progId = progId;
        this.flowSeq = flowSeq;
        this.flowType = flowType;
        this.flowAttr = flowAttr;
        this.flowDesc = flowDesc;
        this.crtdDttm = crtdDttm;
        this.updtdttm = updtdttm;
        this.dltDttm = dltDttm;
    }

    public ProgWorkFlowMng() {
    }
    
    //select node - findSql
    public String findSql() {
        return this.flowAttr.getString("sql");
    }

    //select node - findColInfo
    public List<String> findColInfo() {
        List<String> colList = new ArrayList<>();

        JSONArray colJsonArray = (JSONArray) this.flowAttr.get("col_info");

        for(Object s : colJsonArray) {
            colList.add(s.toString());
        }

        return colList;
    }

    //filter node - findFilterCondList
    public Map<String, String[]> findFilterCondList(List<String> colList) {
        Map<String, String[]> filterCondList = new HashMap<>();

        for(int i = 0; i < colList.size(); i++) {
            JSONArray jsonCondInfo = (JSONArray) this.flowAttr.get(colList.get(i));

            String[] condInfo = new String[jsonCondInfo.length()];
            for(int j = 0; j < jsonCondInfo.length(); j++) {
                condInfo[j] = jsonCondInfo.get(j).toString();
            }

            filterCondList.put(colList.get(i), condInfo);
        }

        return filterCondList;
    }

}
