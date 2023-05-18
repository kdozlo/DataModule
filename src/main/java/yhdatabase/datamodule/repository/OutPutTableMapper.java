package yhdatabase.datamodule.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OutPutTableMapper {
    void createTable(Map<String, String> map);
}
