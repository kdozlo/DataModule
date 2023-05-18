package yhdatabase.datamodule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yhdatabase.datamodule.repository.OutPutTableRepository;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OutPutTableService {

    private final OutPutTableRepository outPutTableRepository;

    public String createTable(String tableNm, List<String> colInfo) {
        String sql = "CREATE TABLE public." + tableNm + " (" +
                "id int8 NOT NULL GENERATED ALWAYS AS IDENTITY, ";

        for(int i = 0; i < colInfo.size(); i++) {
            if(i == colInfo.size() - 1) {
                sql += colInfo.get(i) + " varchar(15));";
            } else {
                sql += colInfo.get(i) + " varchar(15), ";
            }
        }

        outPutTableRepository.createTable(sql);

        return tableNm;
    }
}
