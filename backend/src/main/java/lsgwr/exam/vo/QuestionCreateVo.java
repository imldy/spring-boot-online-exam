/***********************************************************
 * @Description : 问题创建的实体类
 ***********************************************************/
package lsgwr.exam.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class QuestionCreateVo {
    /**
     * 题目名称
     */
    @JsonProperty("name")
    private String questionName;

    @JsonProperty("desc")
    private String questionDescription;

    /**
     * 题目的分数,默认值是5
     */
    @JsonProperty("score")
    private Integer questionScore = 5;

    /**
     * 题目的创建者的id，从token中解析得到
     */
    @JsonProperty("creator")
    private String questionCreatorId;

    /**
     * 问题的难度的id
     */
    @JsonProperty("level")
    private Integer questionLevelId;

    /**
     * 问题的类型(单选、多选、判断等)
     */
    @JsonProperty("type")
    private Integer questionTypeId;

    /**
     * 题目的类别表，从内容角度划分，比如数学、语文、英语等
     */
    @JsonProperty("category")
    private Integer questionCategoryId;

    /**
     * 听力题目的音频文件URL
     */
    @JsonProperty("audioUrl")
    private String audioUrl;

    /**
     * 问题的选项列表，带上了是否是答案的true和false
     */
    @JsonProperty("options")
    private List<QuestionOptionCreateVo> questionOptionCreateVoList;
}
