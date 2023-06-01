package yhdatabase.datamodule.repository;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import yhdatabase.datamodule.domain.ProgMst;
import yhdatabase.datamodule.domain.ProgWorkFlowMng;
import yhdatabase.datamodule.repository.dto.ProgWorkFlowMngDto;

import javax.sql.DataSource;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.*;

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

    public ProgWorkFlowMng save(ProgWorkFlowMng progWorkFlowMng) {
        progWorkFlowMng.setCrtdDttm(LocalDateTime.now());
        SqlParameterSource param = new BeanPropertySqlParameterSource(progWorkFlowMng);
        Number key = jdbcInsert.executeAndReturnKey(param);

        progWorkFlowMng.setFlowId(key.longValue());

        return progWorkFlowMng;
    }

    public int update(ProgWorkFlowMngDto progWorkFlowMngDto) {
        String sql = "update prog_work_flow_mng " + "" +
                "set prog_id=:progId, flow_seq=:flowSeq, flow_type=:flowType, flow_attr=:flowAttr, updt_dttm=:updtDttm " +
                "where flow_id=:flowId";

        progWorkFlowMngDto.setUpdtdttm(LocalDateTime.now());

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("progId", progWorkFlowMngDto.getProgId())
                .addValue("flowSeq", progWorkFlowMngDto.getFlowSeq())
                .addValue("flowType", progWorkFlowMngDto.getFlowType())
                .addValue("flowAttr", progWorkFlowMngDto.getFlowAttr(), Types.OTHER)
                .addValue("updtDttm", progWorkFlowMngDto.getUpdtdttm())
                .addValue("flowId", progWorkFlowMngDto.getFlowId());

        return template.update(sql, param);
    }

    public int delete(Long flowId) {
        String sql = "delete from prog_work_flow_mng where flow_id = :flowId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("flowId", flowId);

        return template.update(sql, param);
    }


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

    //화면의 노드 정보들을 모두 가져옴, 이때 flowseq 오름차순으로 정렬 => 노드 순서대로 결과 처리 편함
    public List<ProgWorkFlowMng> findByProgId(Long progId) {
        String sql = "select * from prog_work_flow_mng where prog_id = :progId order by flow_seq";

        Map<String, Object> param = Map.of("progId", progId);
        List<ProgWorkFlowMng> pwfList = template.query(sql, param, progWorkFlowMngRowMapper());

        //pwfList test
        System.out.println("pwfList test");
        for(int i = 0; i < pwfList.size(); i++) {
            System.out.print(pwfList.get(i) + " - ");
            System.out.print(pwfList.get(i).getFlowSeq() +", ");
            System.out.println(pwfList.get(i).getFlowId());
        }

        return pwfList;
    }

    private RowMapper<ProgWorkFlowMng> progWorkFlowMngRowMapper() {
        return BeanPropertyRowMapper.newInstance(ProgWorkFlowMng.class); //camel 변환 지원
    }


    /*private RowMapper<ProgWorkFlowMng> progWorkFlowMngRowMapper() {
        return ((rs, rowNum) -> {
            ProgWorkFlowMng progWorkFlowMng = new ProgWorkFlowMng();
            progWorkFlowMng.setFlowId(rs.getLong("flow_id"));
            progWorkFlowMng.setProgId(rs.getLong("prog_id"));
            progWorkFlowMng.setFlowSeq(rs.getInt("flow_seq"));
            progWorkFlowMng.setFlowType(rs.getString("flow_type"));
            progWorkFlowMng.setFlowAttr(new JSONObject(rs.getString("flow_attr")));
            progWorkFlowMng.setCrtdDttm(rs.getObject("crtd_dttm", LocalDateTime.class));
            progWorkFlowMng.setUpdtdttm(rs.getObject("updt_dttm", LocalDateTime.class));
            progWorkFlowMng.setDltDttm(rs.getObject("dlt_dttm", LocalDateTime.class));
            return progWorkFlowMng;
        });
    }*/
}
