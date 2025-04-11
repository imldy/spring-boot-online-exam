/***********************************************************
 * @Description : 前端创建问题时的下拉列表选择
 ***********************************************************/
package lsgwr.exam.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lsgwr.exam.entity.QuestionCategory;
import lsgwr.exam.entity.QuestionLevel;
import lsgwr.exam.entity.QuestionType;
import lombok.Data;

import java.util.List;

@Data
public class QuestionSelectionVo {
    @JsonProperty("types")
    private List<QuestionType> questionTypeList;

    @JsonProperty("categories")
    private List<QuestionCategory> questionCategoryList;

    @JsonProperty("levels")
    private List<QuestionLevel> questionLevelList;
}
