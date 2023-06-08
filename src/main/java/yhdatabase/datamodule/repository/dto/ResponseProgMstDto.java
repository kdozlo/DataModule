package yhdatabase.datamodule.repository.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ResponseProgMstDto {
    private Long progId;

    private String progNm;

    private String progDesc;

    private String viewAttr;

    private Boolean useYn;

    private LocalDateTime crtdDttm;

    private LocalDateTime updtDttm;

    private LocalDateTime dltDttm;

    public ResponseProgMstDto(Long progId, String progNm, String progDesc, String viewAttr, Boolean useYn, LocalDateTime crtdDttm, LocalDateTime updtDttm, LocalDateTime dltDttm) {
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
