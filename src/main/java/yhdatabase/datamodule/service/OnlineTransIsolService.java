package yhdatabase.datamodule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yhdatabase.datamodule.domain.ProgWorkFlowMng;
import yhdatabase.datamodule.repository.OnlineTransIsolRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OnlineTransIsolService {

    private final OnlineTransIsolRepository onlineTransIsolRepository;

    @Transactional
    public List<Map<String, Object>> findSQLResult(Optional<ProgWorkFlowMng> progWorkFlowMng) {

        List<Map<String, Object>> result = onlineTransIsolRepository.findSQLResult(progWorkFlowMng);

        System.out.println("findSQLResult");
        for (Map<String, Object> row : result) {
            // this.flowAttr.getString("col") 배열 값 보기 가능 ex. {"col" : ["idx", "user_no", "yyyymmdd"]}"
            for( String key : row.keySet() ){
                Object value = row.get(key);
                System.out.printf(key+" : "+value + " ");
            }
            System.out.println();
        }

        return result;
    }

    public List<Map<String, Object>> filterSQLResult(List<Map<String, Object>> sqlResult, Optional<ProgWorkFlowMng> progWorkFlowMng) {

        List<Map<String, Object>> result = new ArrayList<>();
        List<String> colList = progWorkFlowMng.get().findColInfo();
        Map<String, String[]> filterCondList = progWorkFlowMng.get().findFilterCondList(colList);

        System.out.println("filterSQLResult");

        for (Map<String, Object> row : sqlResult) {
            int checkCond = 0;
            int condCnt = 0;
            for( String key : filterCondList.keySet() ){
                String cond = filterCondList.get(key)[0];
                String condValue = filterCondList.get(key)[1];

                switch (cond) {
                    case ">":
                        condCnt++;
                        if (Integer.parseInt(row.get(key).toString()) > Integer.parseInt(condValue)) {
                            checkCond++;
                        }
                        break;

                    case "<" :
                        condCnt++;
                        if (Integer.parseInt(row.get(key).toString()) < Integer.parseInt(condValue)) {
                            checkCond++;
                        }

                        break;

                    case ">=" :
                        condCnt++;
                        if (Integer.parseInt(row.get(key).toString()) >= Integer.parseInt(condValue)) {
                            checkCond++;
                        }

                        break;

                    case "<=" :
                        condCnt++;
                        if (Integer.parseInt(row.get(key).toString()) <= Integer.parseInt(condValue)) {
                            checkCond++;
                        }

                        break;

                    case "=" :
                        condCnt++;
                        if (row.get(key).toString().equals(condValue)) {
                            checkCond++;
                        }
                        break;

                    case "LIKE":
                        condCnt++;
                        if (row.get(key).toString().contains(condValue)) {
                            checkCond++;
                        }
                        break;

                    case "NOT LIKE":
                        condCnt++;
                        if (!row.get(key).toString().contains(condValue)) {
                            checkCond++;
                        }
                        break;
                }
            }

            if(condCnt == checkCond)
                result.add(row);
        }

        //filterSQLResult 출력값
        for (Map<String, Object> row : result) {
            for( String key : row.keySet() ){
                Object value = row.get(key);
                System.out.printf(key+" : "+value + " ");
            }
            System.out.println();
        }

        return result;
    }
}