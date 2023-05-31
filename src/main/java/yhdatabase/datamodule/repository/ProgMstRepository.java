package yhdatabase.datamodule.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import yhdatabase.datamodule.domain.ProgMst;
import yhdatabase.datamodule.repository.dto.ProgMst2;
import yhdatabase.datamodule.repository.dto.ProgMstDto;

import javax.sql.DataSource;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
public class ProgMstRepository {

    // 추가한 부분
    @Autowired
    private ObjectMapper objectMapper;

    private final NamedParameterJdbcTemplate template;

    private final SimpleJdbcInsert jdbcInsert;


    public ProgMstRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("prog_mst")
                .usingGeneratedKeyColumns("prog_id");
    }

    public ProgMst save(ProgMst progMst) {
        progMst.setCrtdDttm(LocalDateTime.now());
        SqlParameterSource param = new BeanPropertySqlParameterSource(progMst);
        Number key = jdbcInsert.executeAndReturnKey(param);

        progMst.setProgId(key.longValue());

        return progMst;
    }

    public int update(ProgMstDto progMstDto) {
        String sql = "update prog_mst " + "" +
                "set prog_nm=:progNm, prog_desc=:progDesc, view_attr=:viewAttr, use_yn=:useYn, updt_dttm=:updtDttm " +
                "where prog_id=:progId";

        progMstDto.setUpdtDttm(LocalDateTime.now());

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("progNm", progMstDto.getProgNm())
                .addValue("progDesc", progMstDto.getProgDesc())
                .addValue("viewAttr", progMstDto.getViewAttr(), Types.OTHER)
                .addValue("useYn", progMstDto.getUseYn())
                .addValue("updtDttm", progMstDto.getUpdtDttm())
                .addValue("progId", progMstDto.getProgId());

        return template.update(sql, param);
    }

    public int delete(Long progId) {
        String sql = "delete from prog_mst where prog_id = :progId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("progId", progId);

        return template.update(sql, param);
    }

    public ProgMst2 load(Long progId) {
        String sql = "SELECT * FROM prog_mst WHERE prog_id = :progId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("progId", progId);

        try {
            ProgMst2 result = template.queryForObject(
                    sql,
                    param,
                    (rs, rowNum) -> new ProgMst2(
                            rs.getLong("prog_id"),
                            rs.getString("prog_nm"),
                            rs.getString("prog_desc"),
                            rs.getString("view_attr"), // JSON data handled as string
                            rs.getBoolean("use_yn"),
                            rs.getTimestamp("crtd_dttm").toLocalDateTime(),
                            rs.getTimestamp("updt_dttm") != null ? rs.getTimestamp("updt_dttm").toLocalDateTime() : null,
                            rs.getTimestamp("dlt_dttm") != null ? rs.getTimestamp("dlt_dttm").toLocalDateTime() : null
                    )
            );
            System.out.println(result.getViewAttr());
            return result;
        } catch (DataAccessException ex) {
            throw new IllegalArgumentException("No ProgMst found with progId: " + progId);
        }
    }
}
