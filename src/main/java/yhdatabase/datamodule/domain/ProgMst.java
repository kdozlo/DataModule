package yhdatabase.datamodule.domain;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProgMst {
    private Long progId;

    private String progNm;

    private String progDesc;

    private JSONObject viewAttr;

    private Boolean useYn;

    private LocalDateTime crtdDttm;

    private LocalDateTime updtdttm;

    private LocalDateTime dltDttm;

    public ProgMst(String progNm, String progDesc, JSONObject viewAttr, Boolean useYn,
                   LocalDateTime crtdDttm, LocalDateTime updtdttm, LocalDateTime dltDttm) {
        this.progNm = progNm;
        this.progDesc = progDesc;
        this.viewAttr = viewAttr;
        this.useYn = useYn;
        this.crtdDttm = crtdDttm;
        this.updtdttm = updtdttm;
        this.dltDttm = dltDttm;
    }
}
