package yhdatabase.datamodule.repository.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProgMstDto {
    private Long progId;

    private String progNm;

    private String progDesc;

    private JSONObject viewAttr;

    private Boolean useYn;

    private LocalDateTime updtDttm;


    public ProgMstDto(Long progId, String progNm, String progDesc, JSONObject viewAttr, Boolean useYn, LocalDateTime updtDttm) {
        this.progId = progId;
        this.progNm = progNm;
        this.progDesc = progDesc;
        this.viewAttr = viewAttr;
        this.useYn = useYn;
        this.updtDttm = updtDttm;
    }
}
