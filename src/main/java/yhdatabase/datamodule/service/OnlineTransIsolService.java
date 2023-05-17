package yhdatabase.datamodule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yhdatabase.datamodule.domain.ProgWorkFlowMng;
import yhdatabase.datamodule.repository.OnlineTransIsolRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OnlineTransIsolService {

    private final OnlineTransIsolRepository onlineTransIsolRepository;

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

    public List<Map<String, Object>> filterSQLResult(List<Map<String, Object>> sqlResult, Optional<ProgWorkFlowMng> progWorkFlowMng) {

        List<Map<String, Object>> result = new ArrayList<>();

        System.out.println("filterSQLResult");

        for (Map<String, Object> row : sqlResult) {
            // this.flowAttr.getString("col") 배열 값 보기 가능 ex. {"col" : ["idx", "user_no", "yyyymmdd"]}"
            for( String key : row.keySet() ){
                Object value = row.get(key);

                //해당하는 col 찾기
                if(key.equals("user_no")) {
                    //해당하는 col의 조건식 찾기
                    //조건에 만족하는 값 찾기
                    if (Integer.parseInt(value.toString()) > 13) {
                        result.add(row);
                    }
                }
            }
        }

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
