package yhdatabase.datamodule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yhdatabase.datamodule.domain.ProgWorkFlowMng;
import yhdatabase.datamodule.repository.ProgWorkFlowMngRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProgWorkFlowMngService {

    private final ProgWorkFlowMngRepository progWorkFlowMngRepository;

    public ProgWorkFlowMng save(ProgWorkFlowMng progWorkFlowMng) {
        return progWorkFlowMngRepository.save(progWorkFlowMng);
    }

    public Optional<ProgWorkFlowMng> findById(Long flowId) {
        return progWorkFlowMngRepository.findById(flowId);
    }

}
