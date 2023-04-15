package yhdatabase.datamodule.repository;

import org.assertj.core.api.Assertions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import yhdatabase.datamodule.domain.ProgMst;
import yhdatabase.datamodule.domain.ProgWorkFlowMng;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@SpringBootTest
class ProgWorkFlowMngRepositoryTest {

    @Autowired
    ProgWorkFlowMngRepository progWorkFlowMngRepository;

    @Autowired
    ProgMstRepository progMstRepository;

    @Test
    void save() {
        ProgMst tempProgMst = new ProgMst("test",
                "pratice", null, false,
                null, null, null);

        ProgMst saveProgMst = progMstRepository.save(tempProgMst);

        JSONObject tempData = new JSONObject();

        tempData.put("sql", "SELECT  TO_CHAR(SYSDATE,'YYYYMMDD') AS LOG_DATE, "+
                "TO_CHAR(SYSTIMESTAMP,'HH24MISSFF2')||'1' AS LOG_TIME, " +
                "USER_ID||'02' AS LOG_USER_ID, DBMS_RANDOM.RANDOM AS LOG_PAY_ACC FROM YFDS_SHINHAN.ONLINE_TRANS_ISOL " +
                "where rownum <= 10 and rownum = #{A}");

        List<String> tempColData = new ArrayList<>();

        tempColData.add("LOG_DATE");
        tempColData.add("LOG_TIME");
        tempColData.add("USER_ID");

        JSONArray ja = new JSONArray(tempColData);

        tempData.put("column_info", ja);

        ProgWorkFlowMng addData = new ProgWorkFlowMng(null, saveProgMst.getProgId(), 1, "sql", tempData, "practice", null, null, null);

        progWorkFlowMngRepository.save(addData);
    }

    @Test
    void findById() {
        ProgMst tempProgMst = new ProgMst("test",
                "pratice", null, false,
                null, null, null);

        ProgMst saveProgMst = progMstRepository.save(tempProgMst);

        JSONObject tempData = new JSONObject();

        tempData.put("sql", "SELECT  TO_CHAR(SYSDATE,'YYYYMMDD') AS LOG_DATE, "+
                "TO_CHAR(SYSTIMESTAMP,'HH24MISSFF2')||'1' AS LOG_TIME, " +
                "USER_ID||'02' AS LOG_USER_ID, DBMS_RANDOM.RANDOM AS LOG_PAY_ACC FROM YFDS_SHINHAN.ONLINE_TRANS_ISOL " +
                "where rownum <= 10 and rownum = #{A}");

        List<String> tempColData = new ArrayList<>();

        tempColData.add("LOG_DATE");
        tempColData.add("LOG_TIME");
        tempColData.add("USER_ID");

        JSONArray ja = new JSONArray(tempColData);

        tempData.put("column_info", ja);

        ProgWorkFlowMng addData = new ProgWorkFlowMng(null, saveProgMst.getProgId(), 1, "sql", tempData, "practice", null, null, null);

        progWorkFlowMngRepository.save(addData);

        Optional<ProgWorkFlowMng> resultData = progWorkFlowMngRepository.findById(addData.getFlowId());

        Assertions.assertThat(addData.getFlowId()).isEqualTo(resultData.get().getFlowId());


    }

}