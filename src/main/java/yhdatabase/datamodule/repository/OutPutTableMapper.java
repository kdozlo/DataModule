package yhdatabase.datamodule.repository;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OutPutTableMapper {
    void creatTable(Long progId, List<String> colInfo);
}
