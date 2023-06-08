package yhdatabase.datamodule.repository.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ProgWorkFlowMngDto {

    private Long flowId;

    private Long progId;

    private int flowSeq;

    private String flowType;

    private JSONObject flowAttr;

    private LocalDateTime updtdttm;

    public ProgWorkFlowMngDto(Long flowId, Long progId, int flowSeq, String flowType, JSONObject flowAttr, LocalDateTime updtdttm) {
        this.flowId = flowId;
        this.progId = progId;
        this.flowSeq = flowSeq;
        this.flowType = flowType;
        this.flowAttr = flowAttr;
        this.updtdttm = updtdttm;
    }
}
