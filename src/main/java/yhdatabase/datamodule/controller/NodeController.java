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
                    System.out.println("개수 : " + outPutTableService.processResult(result, cur));

                    break;
            }
        }


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

}