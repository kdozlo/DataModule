package yhdatabase.datamodule.repository;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.transaction.annotation.Transactional;
import yhdatabase.datamodule.domain.ProgWorkFlowMng;
import yhdatabase.datamodule.service.SqlResultDto;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class ProgWorkFlowMngRepository {

    private final NamedParameterJdbcTemplate template;

    private final SimpleJdbcInsert jdbcInsert;

    public ProgWorkFlowMngRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("prog_work_flow_mng")
                .usingGeneratedKeyColumns("flow_id");
    }

    @Transactional
    public ProgWorkFlowMng save(ProgWorkFlowMng progWorkFlowMng) {
        progWorkFlowMng.setCrtdDttm(LocalDateTime.now());
        SqlParameterSource param = new BeanPropertySqlParameterSource(progWorkFlowMng);
        Number key = jdbcInsert.executeAndReturnKey(param);

        progWorkFlowMng.setFlowId(key.longValue());

        return progWorkFlowMng;
    }

    @Transactional
    public Optional<ProgWorkFlowMng> findById(Long flowId) {
        String sql = "select * from prog_work_flow_mng where flow_id = :flowId";

        try {
            Map<String, Object> param = Map.of("flowId", flowId);
            ProgWorkFlowMng progWorkFlowMng = template.queryForObject(sql, param, progWorkFlowMngRowMapper());
            return Optional.of(progWorkFlowMng);
        } catch (EmptyResultDataAccessException e) {
            log.info("Optional<ProgWorkFlowMng>.empty(), flow_id={}", flowId);
            return Optional.empty();
        }
    }

    private RowMapper<ProgWorkFlowMng> progWorkFlowMngRowMapper() {
        return ((rs, rowNum) -> {
            ProgWorkFlowMng progWorkFlowMng = new ProgWorkFlowMng();
            progWorkFlowMng.setFlowId(rs.getLong("flow_id"));
            progWorkFlowMng.setProgId(rs.getLong("prog_id"));
            progWorkFlowMng.setFlowSeq(rs.getInt("flow_seq"));
            progWorkFlowMng.setFlowType(rs.getString("flow_type"));
            progWorkFlowMng.setFlowAttr(new JSONObject(rs.getString("flow_attr")));
            progWorkFlowMng.setFlowDesc(rs.getString("flow_desc"));
            progWorkFlowMng.setCrtdDttm(rs.getObject("crtd_dttm", LocalDateTime.class));
            progWorkFlowMng.setUpdtdttm(rs.getObject("updt_dttm", LocalDateTime.class));
            progWorkFlowMng.setDltDttm(rs.getObject("dlt_dttm", LocalDateTime.class));
            return progWorkFlowMng;
        });
    }
}
