package yhdatabase.datamodule.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OutPutTableRepository {
    private final OutPutTableMapper outPutTableMapper;


}
