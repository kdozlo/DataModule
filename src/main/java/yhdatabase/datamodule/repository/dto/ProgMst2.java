package yhdatabase.datamodule.repository.dto;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProgMst2 {
    private Long progId;

    private String progNm;

    private String progDesc;

    private String viewAttr;

    private Boolean useYn;

    private LocalDateTime crtdDttm;

    private LocalDateTime updtDttm;

    private LocalDateTime dltDttm;

    public ProgMst2(long progId, String progNm, String progDesc, String viewAttr, Boolean useYn,
                   LocalDateTime crtdDttm, LocalDateTime updtDttm, LocalDateTime dltDttm) {
        this.progId = progId;
        this.progNm = progNm;
        this.progDesc = progDesc;
        this.viewAttr = viewAttr;
        this.useYn = useYn;
        this.crtdDttm = crtdDttm;
        this.updtDttm = updtDttm;
        this.dltDttm = dltDttm;
    }
}
