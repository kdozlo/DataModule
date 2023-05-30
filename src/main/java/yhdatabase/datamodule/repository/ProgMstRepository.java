package yhdatabase.datamodule.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.transaction.annotation.Transactional;
import yhdatabase.datamodule.domain.ProgMst;
import yhdatabase.datamodule.repository.dto.ProgMstDto;

import javax.sql.DataSource;
import java.time.LocalDateTime;

@Slf4j
public class ProgMstRepository {

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

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("progNm", progMstDto.getProgNm())
                .addValue("progDesc", progMstDto.getProgDesc())
                .addValue("viewAttr", progMstDto.getViewAttr())
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
}
