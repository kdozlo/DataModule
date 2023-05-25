package yhdatabase.datamodule.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import yhdatabase.datamodule.domain.ProgMst;
import yhdatabase.datamodule.domain.ProgWorkFlowMng;
import yhdatabase.datamodule.repository.ProgWorkFlowMngRepository;
import yhdatabase.datamodule.service.OnlineTransIsolService;
import yhdatabase.datamodule.service.OutPutTableService;
import yhdatabase.datamodule.service.ProgMstService;
import yhdatabase.datamodule.service.ProgWorkFlowMngService;

import java.util.*;

@Slf4j
@Controller
@RequestMapping("api/diagram")
@RequiredArgsConstructor
//@CrossOrigin(origins = "*")
public class NodeController {
    private final ProgMstService progMstService;

    private final ProgWorkFlowMngService progWorkFlowMngService;

    private final OnlineTransIsolService onlineTransIsolService;

    private final OutPutTableService outPutTableService;

    @PostMapping("/project")
    public String saveProgMst(@RequestBody ProgMst progMst, RedirectAttributes redirectAttributes) {
        ProgMst savedProgMst = progMstService.save(progMst);
        redirectAttributes.addAttribute("progId", savedProgMst.getProgId());
        redirectAttributes.addAttribute("crtdDttm", savedProgMst.getCrtdDttm());

        return "redirect:/project/{progId}";
    }

    @PostMapping("/project/savenode/{progId}")
    public String saveProgWorkFlowMng(@RequestBody ProgWorkFlowMng progWorkFlowMng, RedirectAttributes redirectAttributes) {

        ProgWorkFlowMng savedProgWorkFlowMng = progWorkFlowMngService.save(progWorkFlowMng);
        redirectAttributes.addAttribute("flowId", savedProgWorkFlowMng.getFlowId());

        return "redirect:/project/sqlResult/{progId}/{flowId}";
    }

    @GetMapping("/project/sqlResult/{progId}")
    public List<Map<String, Object>> getResult(@PathVariable String progId){
        List<ProgWorkFlowMng> nodeList = progWorkFlowMngService.findByProgId(Long.parseLong(progId));

        List<Map<String, Object>> result = null;

        for (ProgWorkFlowMng cur : nodeList) {
            String flowType = cur.getFlowType();

            switch(flowType) {
                case "select" :
                    result = onlineTransIsolService.findSQLResult(Optional.of(cur));
                    break;
                case "filter" :
                    result = onlineTransIsolService.filterSQLResult(result, Optional.of(cur));
                    break;
                case "output" :
                    String tableNm = "table" + "_" + cur.getProgId().toString();



                    outPutTableService.createTable(tableNm, cur);
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