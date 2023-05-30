package yhdatabase.datamodule.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import yhdatabase.datamodule.domain.ProgMst;
import yhdatabase.datamodule.service.ProgMstService;

@Slf4j
@Controller
@RequestMapping("diagram")
@RequiredArgsConstructor
public class ProgMstController {

    private final ProgMstService progMstService;

    @PostMapping("/project")
    public String saveProgMst(@RequestBody ProgMst progMst, RedirectAttributes redirectAttributes) {
        ProgMst savedProgMst = progMstService.save(progMst);
        redirectAttributes.addAttribute("progId", savedProgMst.getProgId());
        redirectAttributes.addAttribute("crtdDttm", savedProgMst.getCrtdDttm());

        return "redirect:/project/{progId}";
    }



    @PostMapping("/project/{progId}")
    public String deleteProgMst(@PathVariable String progId, RedirectAttributes redirectAttributes) {
        int deleteCnt = progMstService.delete(Long.parseLong(progId));

        redirectAttributes.addAttribute("deleteCnt", deleteCnt);

        return "redirect:/project";
    }

}
