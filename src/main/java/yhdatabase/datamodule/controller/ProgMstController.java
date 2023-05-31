package yhdatabase.datamodule.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import yhdatabase.datamodule.domain.ProgMst;
import yhdatabase.datamodule.repository.dto.ProgMst2;
import yhdatabase.datamodule.repository.dto.ProgMstDto;
import yhdatabase.datamodule.service.ProgMstService;

@Slf4j
@RestController
@RequestMapping("diagram")
@RequiredArgsConstructor
public class ProgMstController {

    private final ProgMstService progMstService;

//    @PostMapping("/project")
//    public String saveProgMst(@RequestBody ProgMst progMst, RedirectAttributes redirectAttributes) {
//        ProgMst savedProgMst = progMstService.save(progMst);
//        redirectAttributes.addAttribute("progId", savedProgMst.getProgId());
//        redirectAttributes.addAttribute("crtdDttm", savedProgMst.getCrtdDttm());
//
//        return "redirect:/project/{progId}";
//    }

    @PostMapping("/project")
    public long saveProgMst(@RequestBody ProgMst progMst, RedirectAttributes redirectAttributes) {
        ProgMst savedProgMst = progMstService.save(progMst);
        redirectAttributes.addAttribute("progId", savedProgMst.getProgId());
        redirectAttributes.addAttribute("crtdDttm", savedProgMst.getCrtdDttm());

        return savedProgMst.getProgId();
    }

    @PostMapping("/project/update/{progId}")
    public String updateProgMst(@RequestBody ProgMstDto progMstDto) {
        if (progMstService.update(progMstDto) > 0)
            return "success";
        else
            return "fail";
    }

    @PostMapping("/project/delete/{progId}")
    public String deleteProgMst(@PathVariable String progId, RedirectAttributes redirectAttributes) {
        int deleteCnt = progMstService.delete(Long.parseLong(progId));

        redirectAttributes.addAttribute("deleteCnt", deleteCnt);

        return "redirect:/diagram";
    }

    @PostMapping("/project/load/{progId}")
    public ProgMst2 loadProgMst(@PathVariable String progId) {
        return progMstService.load(Long.parseLong(progId));
    }
}
