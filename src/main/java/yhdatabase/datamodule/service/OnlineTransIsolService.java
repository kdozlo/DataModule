package yhdatabase.datamodule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yhdatabase.datamodule.domain.ProgWorkFlowMng;
import yhdatabase.datamodule.repository.OnlineTransIsolRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OnlineTransIsolService {

    private final OnlineTransIsolRepository onlineTransIsolRepository;

    public List<Map<String, Object>> findSQLResult(Optional<ProgWorkFlowMng> progWorkFlowMng) {

        List<Map<String, Object>> result = onlineTransIsolRepository.findSQLResult(progWorkFlowMng);

        for (Map<String, Object> row : result) {
            // this.flowAttr.getString("col") 배열 값 보기 가능 ex. {"col" : ["idx", "user_no", "yyyymmdd"]}"
            for( String key : row.keySet() ){
                Object value = row.get(key);
                System.out.print( String.format(key+" : "+value));
            }
            System.out.println();
        }

        return result;
    }

}
