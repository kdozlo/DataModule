package yhdatabase.datamodule.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.transaction.annotation.Transactional;
import yhdatabase.datamodule.domain.ProgMst;

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

    @Transactional
    public ProgMst save(ProgMst progMst) {
        progMst.setCrtdDttm(LocalDateTime.now());
        SqlParameterSource param = new BeanPropertySqlParameterSource(progMst);
        Number key = jdbcInsert.executeAndReturnKey(param);

        progMst.setProgId(key.longValue());

        return progMst;
    }
}
