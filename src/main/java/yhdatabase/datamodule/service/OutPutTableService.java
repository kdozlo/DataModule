package yhdatabase.datamodule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yhdatabase.datamodule.domain.ProgWorkFlowMng;
import yhdatabase.datamodule.repository.OutPutTableRepository;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OutPutTableService {

    private final OutPutTableRepository outPutTableRepository;

    public int processOutputNode(List<Map<String, Object>> result, ProgWorkFlowMng cur) {
        int resultNum = 0;
        String type = cur.findType();
        String tableNm = cur.findTableName();
        List<String> colInfo = cur.findColInfo();
        Map<String, String[]> condList = cur.findCondList(colInfo);

        switch(type) {
            case "insert" :
                resultNum = outPutTableRepository.insertResult(result, tableNm, condList);

                break;
            case "update" :
                List<String> pk = cur.findPk();
                resultNum = outPutTableRepository.updateResult( result, tableNm, condList, pk);
                break;
            case "delete" :

                break;
        }

        return resultNum;
    }

    public int insertResultToCreateTable(String tableNm, List<Map<String, Object>> result) {
        return outPutTableRepository.insertResultToCreateTable(tableNm, result);
    }

    public String createTable(String tableNm, ProgWorkFlowMng cur) {
        List<String> colList = cur.findColInfo();
        Map<String, String[]> outputCondList = cur.findCondList(colList);


        String sql ="DROP TABLE IF EXISTS " + tableNm + "; CREATE TABLE public." + tableNm + " (" +
                "id int8 NOT NULL GENERATED ALWAYS AS IDENTITY, ";

        for(String s : colList) {
            if(s.equals(colList.get(colList.size() - 1))) {
                sql += s + " " + outputCondList.get(s)[2] +");";
            } else {
                sql += s + " "+ outputCondList.get(s)[2] + ", ";
            }
        }

        outPutTableRepository.createTable(sql);

        return tableNm;
    }


}
