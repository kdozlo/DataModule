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
                System.out.print( String.format(key+" : "+value + " "));
            }
            System.out.println();
        }

        return result;
    }

    public Set<Map<String, Object>> filterSQLResult(List<Map<String, Object>> sqlResult, Optional<ProgWorkFlowMng> progWorkFlowMng) {

        Set<Map<String, Object>> result = new HashSet<>();

        Map<String, String[]> filterCondList = new HashMap<>();

        System.out.println("filterSQLResult");

        for (Map<String, Object> row : sqlResult) {
            for( String key : filterCondList.keySet() ){
                String cond = filterCondList.get(key)[0];
                String condValue = filterCondList.get(key)[1];

                switch (cond) {
                    case ">":
                        if (Integer.parseInt(row.get(key).toString()) > Integer.parseInt(condValue)) {
                            result.add(row);
                        }
                        break;

                    case "<" :
                        if (Integer.parseInt(row.get(key).toString()) < Integer.parseInt(condValue)) {
                            result.add(row);
                        }

                        break;

                    case ">=" :
                        if (Integer.parseInt(row.get(key).toString()) >= Integer.parseInt(condValue)) {
                            result.add(row);
                        }

                        break;

                    case "<=" :
                        if (Integer.parseInt(row.get(key).toString()) <= Integer.parseInt(condValue)) {
                            result.add(row);
                        }

                        break;

                    case "=" :
                        if (row.get(key).toString().equals(condValue)) {
                            result.add(row);
                        }
                        break;

                    case "LIKE":
                        if (row.get(key).toString().contains(condValue)) {
                            result.add(row);
                        }
                        break;

                    case "NOT LIKE":
                        if (!row.get(key).toString().contains(condValue)) {
                            result.add(row);
                        }
                        break;
                }
            }
        }

        //filterSQLResult 출력값
        for (Map<String, Object> row : result) {
            // this.flowAttr.getString("col") 배열 값 보기 가능 ex. {"col" : ["idx", "user_no", "yyyymmdd"]}"
            for( String key : row.keySet() ){
                Object value = row.get(key);
                System.out.print( String.format(key+" : "+value + " "));
            }
            System.out.println();
        }

        return result;
    }
}
