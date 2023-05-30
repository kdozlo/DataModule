package yhdatabase.datamodule.repository.dto;

import lombok.Data;
import org.json.JSONObject;

import java.time.LocalDateTime;

@Data
public class ProgMstDto {
    private Long progId;

    private String progNm;

    private String progDesc;

    private JSONObject viewAttr;

    private Boolean useYn;

    private LocalDateTime updtDttm;


    public ProgMstDto(String progNm, String progDesc, JSONObject viewAttr, Boolean useYn, LocalDateTime updtDttm) {
        this.progNm = progNm;
        this.progDesc = progDesc;
        this.viewAttr = viewAttr;
        this.useYn = useYn;
        this.updtDttm = updtDttm;
    }
}
