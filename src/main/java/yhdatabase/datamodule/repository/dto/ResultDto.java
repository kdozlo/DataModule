package yhdatabase.datamodule.repository.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultDto {
    //select, filter, output
    String nodeType;

    //처리된 데이터 개수
    int resultNum;

    //실행 시간
    String timeDiff;

    public ResultDto(String nodeType, int resultNum, String timeDiff) {
        this.nodeType = nodeType;
        this.resultNum = resultNum;
        this.timeDiff = timeDiff;
    }
}
