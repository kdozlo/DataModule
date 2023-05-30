package yhdatabase.datamodule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yhdatabase.datamodule.domain.ProgWorkFlowMng;
import yhdatabase.datamodule.repository.ProgWorkFlowMngRepository;
import yhdatabase.datamodule.repository.dto.ProgWorkFlowMngDto;

import java.util.List;
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
    public int update(ProgWorkFlowMngDto progWorkFlowMngDto) {
        return progWorkFlowMngRepository.update(progWorkFlowMngDto);}

    @Transactional
    public int delete(Long progId) {
        return progWorkFlowMngRepository.delete(progId);}

    @Transactional
    public Optional<ProgWorkFlowMng> findById(Long flowId) {
        return progWorkFlowMngRepository.findById(flowId);
    }

    @Transactional
    public List<ProgWorkFlowMng> findByProgId(Long progId) {
        return progWorkFlowMngRepository.findByProgId(progId);
    }

}
