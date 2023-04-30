package yhdatabase.datamodule.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import yhdatabase.datamodule.domain.ProgMst;
import yhdatabase.datamodule.domain.ProgWorkFlowMng;
import yhdatabase.datamodule.repository.ProgWorkFlowMngRepository;
import yhdatabase.datamodule.service.ProgMstService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/diagram")
@RequiredArgsConstructor
public class NodeController {
    private final ProgMstService progMstService;

    private final ProgWorkFlowMngRepository progWorkFlowMngRepository;

    @PostMapping("/project")
    public String saveProgMst(@RequestBody ProgMst progMst, RedirectAttributes redirectAttributes) {
        ProgMst savedProgMst = progMstService.save(progMst);
        redirectAttributes.addAttribute("progId", savedProgMst.getProgId());
        redirectAttributes.addAttribute("crtdDttm", savedProgMst.getCrtdDttm());

        return "redirect:/project/{progId}";
    }

    @PostMapping("/project/{progId}")
    public String saveProgWorkFlowMng(@PathVariable String progId, @RequestBody ProgWorkFlowMng progWorkFlowMng, RedirectAttributes redirectAttributes) {

        ProgWorkFlowMng savedProgWorkFlowMng = progWorkFlowMngRepository.save(progWorkFlowMng);
        redirectAttributes.addAttribute("flowId", savedProgWorkFlowMng.getFlowId());

        return "redirect:/project/{progId}/{flowId}";
    }

   /* @GetMapping("/api/articles")
    public List<Article> getAllArticle(){
        return articleService.getArticles();
    }*/
}
