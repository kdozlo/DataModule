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
import yhdatabase.datamodule.service.ProgMstService;
import yhdatabase.datamodule.service.ProgWorkFlowMngService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/diagram")
@RequiredArgsConstructor
//@CrossOrigin(origins = "*")
public class NodeController {
    private final ProgMstService progMstService;

    private final ProgWorkFlowMngService progWorkFlowMngService;

    private final OnlineTransIsolService onlineTransIsolService;

    @PostMapping("/project")
    public String saveProgMst(@RequestBody ProgMst progMst, RedirectAttributes redirectAttributes) {
        ProgMst savedProgMst = progMstService.save(progMst);
        redirectAttributes.addAttribute("progId", savedProgMst.getProgId());
        redirectAttributes.addAttribute("crtdDttm", savedProgMst.getCrtdDttm());

        return "redirect:/project/{progId}";
    }

    @PostMapping("/project/savenode/{progId}")
    public String saveProgWorkFlowMng(@PathVariable String progId, @RequestBody ProgWorkFlowMng progWorkFlowMng, RedirectAttributes redirectAttributes) {

        ProgWorkFlowMng savedProgWorkFlowMng = progWorkFlowMngService.save(progWorkFlowMng);
        redirectAttributes.addAttribute("flowId", savedProgWorkFlowMng.getFlowId());

        return "redirect:/project/sqlResult/{progId}/{flowId}";
    }

    @GetMapping("/project/sqlResult/{progId}/{flowId}")
    public List<Map<String, Object>> getAllsqlResult(@PathVariable String progId, @PathVariable String flowId){
        Optional<ProgWorkFlowMng> findProgWorkFlowMng = progWorkFlowMngService.findById(Long.parseLong(flowId));
        List<Map<String, Object>> sqlResult = onlineTransIsolService.findSQLResult(findProgWorkFlowMng);
        System.out.println();
        onlineTransIsolService.filterSQLResult(sqlResult, findProgWorkFlowMng);

        return sqlResult;
    }

}
