package yhdatabase.datamodule.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import yhdatabase.datamodule.domain.ProgWorkFlowMng;
import yhdatabase.datamodule.repository.dto.ProgWorkFlowMngDto;
import yhdatabase.datamodule.service.DataProcessService;
import yhdatabase.datamodule.service.OutPutTableService;
import yhdatabase.datamodule.service.ProgWorkFlowMngService;

import java.util.*;

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

        for (ProgWorkFlowMng cur : nodeList) {
            String flowType = cur.getFlowType();

            switch(flowType) {
                case "select" :
                    result = dataProcessService.findSQLResult(Optional.of(cur));
                    break;
                case "filter" :
                    result = dataProcessService.filterSQLResult(result, Optional.of(cur));
                    break;
                case "output" :
                    //String tableNm = "table" + "_" + cur.getProgId().toString();
                    //outPutTableService.createTable(tableNm, cur);
                    //System.out.println("insert 개수 : " + outPutTableService.insertResultToCreateTable(tableNm, result));
                    System.out.println("output 노드 수행된 튜플 개수 : " + outPutTableService.processOutputNode(result, cur));

                    break;
            }
        }

        //필터노드 정보까지만 있음.
        // 필터 노드를 insert/update/delete 처리하여, 다른 테이블에 옮긴 정보들은 옮긴 디비 테이블에서 확인 가능. 여기서는 몇개 처리됬는지만 반환.
        System.out.println("컨트롤러 후");
        for (Map<String, Object> row : result) {
            for( String key : row.keySet() ){
                Object value = row.get(key);
                System.out.printf(key+" : "+value + " ");
            }
            System.out.println();
        }


        return result;
    }

    @GetMapping("project/get-tables")
    public List<String> getTables(){
        return progWorkFlowMngService.getTables();
    }

    @GetMapping("project/{table_name}")
    public List<String> getTableCols(@PathVariable String table_name){
        return progWorkFlowMngService.getTableCols(table_name);
    }
}