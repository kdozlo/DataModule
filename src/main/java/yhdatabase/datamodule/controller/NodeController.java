package yhdatabase.datamodule.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import yhdatabase.datamodule.domain.ProgWorkFlowMng;
import yhdatabase.datamodule.repository.dto.ProgWorkFlowMngDto;
import yhdatabase.datamodule.service.DataProcessService;
import yhdatabase.datamodule.service.OutPutTableService;
import yhdatabase.datamodule.service.ProgWorkFlowMngService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("diagram")
@RequiredArgsConstructor
public class NodeController {

    private final ProgWorkFlowMngService progWorkFlowMngService;

    private final DataProcessService dataProcessService;

    private final OutPutTableService outPutTableService;

    @PostMapping("/project/save-node/{progId}")
    public Long saveProgWorkFlowMng(@RequestBody ProgWorkFlowMng progWorkFlowMng) {

        ProgWorkFlowMng savedProgWorkFlowMng = progWorkFlowMngService.save(progWorkFlowMng);

        return savedProgWorkFlowMng.getFlowId();
    }

    @PostMapping("/project/update-node/{progId}/{flowId}")
    public int updateNode(@RequestBody ProgWorkFlowMngDto progWorkFlowMngDto) {
        return progWorkFlowMngService.update(progWorkFlowMngDto);
    }

    @PostMapping("/project/delete-node/{progId}/{flowId}")
    public int deleteNode(@PathVariable String flowId) {
        return progWorkFlowMngService.delete(Long.parseLong(flowId));
    }

    @GetMapping("/project/sql-result/{progId}")
    public List<Map<String, Object>> getResult(@PathVariable String progId){
        List<ProgWorkFlowMng> nodeList = progWorkFlowMngService.findByProgId(Long.parseLong(progId));

        List<Map<String, Object>> result = null;

        Long start;
        Long end;

        for (ProgWorkFlowMng cur : nodeList) {
            String flowType = cur.getFlowType();

            switch(flowType) {
                case "select" :
                    start = System.currentTimeMillis();

                    result = dataProcessService.findSQLResult(Optional.of(cur));

                    end = System.currentTimeMillis();
                    System.out.print("SQL node - ");
                    timeDiff(start, end);
                    break;
                case "filter" :
                    start = System.currentTimeMillis();

                    result = dataProcessService.filterSQLResult(result, Optional.of(cur));

                    end = System.currentTimeMillis();
                    System.out.print("SQL node - ");
                    timeDiff(start, end);
                    break;
                case "output" :
                    start = System.currentTimeMillis();

                    System.out.println("output 노드 수행된 튜플 개수 : " + outPutTableService.processOutputNode(result, cur));

                    end = System.currentTimeMillis();
                    System.out.print("SQL node - ");
                    timeDiff(start, end);
                    break;
            }
        }

        //필터노드 정보까지만 있음.
        // 필터 노드를 insert/update/delete 처리하여, 다른 테이블에 옮긴 정보들은 옮긴 디비 테이블에서 확인 가능. 여기서는 몇개 처리됬는지만 반환.
        /*System.out.println("컨트롤러 후");
        for (Map<String, Object> row : result) {
            for( String key : row.keySet() ){
                Object value = row.get(key);
                System.out.printf(key+" : "+value + " ");
            }
            System.out.println();
        }*/


        return result;
    }


    public void timeDiff(Long start, Long end) {
        long executionTimeMillis = start - end;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(executionTimeMillis) % 60;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(executionTimeMillis) % 60;
        long hours = TimeUnit.MILLISECONDS.toHours(executionTimeMillis);

        System.out.println("실행 시간: " + hours + "시간 " + minutes + "분 " + seconds + "초");
    }
}