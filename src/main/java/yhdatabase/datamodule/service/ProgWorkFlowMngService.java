package yhdatabase.datamodule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yhdatabase.datamodule.domain.ProgWorkFlowMng;
import yhdatabase.datamodule.repository.ProgWorkFlowMngRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProgWorkFlowMngService {

    private final ProgWorkFlowMngRepository progWorkFlowMngRepository;

    @Transactional
    public ProgWorkFlowMng save(ProgWorkFlowMng progWorkFlowMng) {
        return progWorkFlowMngRepository.save(progWorkFlowMng);
    }

    @Transactional
    public Optional<ProgWorkFlowMng> findById(Long flowId) {
        return progWorkFlowMngRepository.findById(flowId);
    }

}
