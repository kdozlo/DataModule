package yhdatabase.datamodule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yhdatabase.datamodule.domain.ProgMst;
import yhdatabase.datamodule.repository.ProgMstRepository;

@Service
@RequiredArgsConstructor
public class ProgMstService {

    private final ProgMstRepository progMstRepository;

    public ProgMst save(ProgMst progmst) {
        return progMstRepository.save(progmst);
    }

}
