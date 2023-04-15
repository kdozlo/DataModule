package yhdatabase.datamodule.domain;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OnlineTransIsol {

    private Long idx;

    private int userNo;

    private String yyyymmdd;

    private String hhmissff;

    private String outPayBcd;

    private String outPayAcc;

    private String outPayName;

    private String inPayBcd;

    private String inPayAcc;

    private String inPayName;

    private int totAmt;

    private int balance;

    private String acctDesc;

    private String acctType;

    private String userType;

    private String deviceInfo;

    private LocalDateTime inptDttm;

    public OnlineTransIsol() {
    }
}
