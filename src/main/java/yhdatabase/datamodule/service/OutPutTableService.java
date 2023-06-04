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
        List<String> pk;
        switch(type) {
            case "insert" :
                resultNum = outPutTableRepository.insertResult(result, tableNm, condList);

                break;
            case "update" :
                pk = cur.findPk();
                resultNum = outPutTableRepository.updateResult(result, tableNm, condList, pk);
                break;
            case "delete" :
                pk = cur.findPk();
                resultNum = outPutTableRepository.deleteResult(result, tableNm, pk);
                break;
        }

        return resultNum;
    }
}
