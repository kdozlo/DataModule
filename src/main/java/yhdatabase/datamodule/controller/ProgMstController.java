package yhdatabase.datamodule.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import yhdatabase.datamodule.domain.ProgMst;
import yhdatabase.datamodule.repository.dto.ProgMstDto;
import yhdatabase.datamodule.repository.dto.ResponseProgMstDto;
import yhdatabase.datamodule.service.ProgMstService;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("diagram")
@RequiredArgsConstructor
public class ProgMstController {

    private final ProgMstService progMstService;

    @PostMapping("/project")
    public Long saveProgMst(@RequestBody ProgMst progMst) {
        ProgMst savedProgMst = progMstService.save(progMst);

        return savedProgMst.getProgId();
    }

    @PostMapping("/project/load/{progId}")
    public Optional<ResponseProgMstDto> loadProgMst(@PathVariable String progId) {
        return progMstService.findById(Long.parseLong(progId));
    }

    @PostMapping("/project/update/{progId}")
    public String updateProgMst(@RequestBody ProgMstDto progMstDto) {
        if (progMstService.update(progMstDto) > 0)
            return "success";
        else
            return "fail";
    }

    @PostMapping("/project/delete/{progId}")
    public String deleteProgMst(@PathVariable String progId) {

        if (progMstService.delete(Long.parseLong(progId)) > 0)
            return "success";
        else
            return "fail";
    }

}
