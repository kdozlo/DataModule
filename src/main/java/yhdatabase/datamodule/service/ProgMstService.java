package yhdatabase.datamodule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yhdatabase.datamodule.domain.ProgMst;
import yhdatabase.datamodule.repository.ProgMstRepository;
import yhdatabase.datamodule.repository.dto.ProgMstDto;

@Service
@RequiredArgsConstructor
public class ProgMstService {

    private final ProgMstRepository progMstRepository;

    @Transactional
    public ProgMst save(ProgMst progmst) {
        return progMstRepository.save(progmst);
    }

    @Transactional
    public int update(ProgMstDto progMstDto) {
        return progMstRepository.update(progMstDto);
    }

    @Transactional
    public int delete(Long progId) {
        return progMstRepository.delete(progId);}

}
