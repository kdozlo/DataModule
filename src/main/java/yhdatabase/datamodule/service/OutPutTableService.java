package yhdatabase.datamodule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yhdatabase.datamodule.repository.OutPutTableRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OutPutTableService {

    private final OutPutTableRepository outPutTableRepository;

    public Long createTable(Long progId, List<String> colInfo) {
        outPutTableRepository.createTable(progId, colInfo);

        return progId;
    }
}
