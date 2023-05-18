package yhdatabase.datamodule.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OutPutTableRepository {
    private final OutPutTableMapper outPutTableMapper;

    public Long createTable(Long progId, List<String> colInfo) {
        outPutTableMapper.createTable(progId, colInfo);

        return progId;
    }
}
