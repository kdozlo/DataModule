package yhdatabase.datamodule.repository.mybatis;

import yhdatabase.datamodule.domain.OnlineTransIsol;

import java.time.LocalDateTime;

public interface OnlineTransIsolMapper {
    OnlineTransIsol findSQLResult(Long idx, int userNo, String userId, String yyyymmdd, String hhmissff, String outPayBcd,
                                  String outPayAcc, String outPayName, String inPayBcd, String inPayAcc, String inPayName, int totAmt, int balance,
                                  String acctDesc, String acctType, String userType, String deviceInfo, LocalDateTime inptDttm);

}
