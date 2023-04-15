package yhdatabase.datamodule.repository;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import yhdatabase.datamodule.domain.OnlineTransIsol;
import yhdatabase.datamodule.domain.ProgWorkFlowMng;
import yhdatabase.datamodule.service.SqlResultDto;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Slf4j
public class OnlineTransIsolRepository {

    ProgWorkFlowMngRepository progWorkFlowMngRepository;

    private final NamedParameterJdbcTemplate template;

    private final SimpleJdbcInsert jdbcInsert;

    public OnlineTransIsolRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("online_trans_isol")
                .usingGeneratedKeyColumns("idx");
    }

   /* public List<OnlineTransIsol> findAllById(Long flowId) {
        Optional<ProgWorkFlowMng> progWorkFlowMng = progWorkFlowMngRepository.findById(flowId);
        String sql = progWorkFlowMng.get().findSql();




        return null;
    }*/
}
