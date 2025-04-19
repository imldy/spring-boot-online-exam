/***********************************************************
 * @Description : 按照单选、多选和判断题返回对应的问题列表
 ***********************************************************/
package lsgwr.exam.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ExamQuestionTypeVo {
    @JsonProperty("partIs")
    private List<ExamQuestionSelectVo> examQuestionSelectVoPartIList;

    @JsonProperty("partIIAs")
    private List<ExamQuestionSelectVo> examQuestionSelectVoPartIIAList;

    @JsonProperty("partIIBs")
    private List<ExamQuestionSelectVo> examQuestionSelectVoPartIIBList;

    @JsonProperty("partIICs")
    private List<ExamQuestionSelectVo> examQuestionSelectVoPartIICList;

    @JsonProperty("partIIIAs")
    private List<ExamQuestionSelectVo> examQuestionSelectVoPartIIIAList;

    @JsonProperty("partIIIBs")
    private List<ExamQuestionSelectVo> examQuestionSelectVoPartIIIBList;

    @JsonProperty("partIIICs")
    private List<ExamQuestionSelectVo> examQuestionSelectVoPartIIICList;

    @JsonProperty("partIVs")
    private List<ExamQuestionSelectVo> examQuestionSelectVoPartIVList;
}
