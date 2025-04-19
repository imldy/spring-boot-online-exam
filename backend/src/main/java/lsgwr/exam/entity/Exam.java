/***********************************************************
 * @Description : 考试表，要有题目、总分数、时间限制、有效日期、创建者等字段
 ***********************************************************/

package lsgwr.exam.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
public class Exam {
    @Id
    private String examId;
    private String examName;
    private String examAvatar;
    private String examDescription;
    private String examQuestionIds;
    private String examQuestionIdsPartI;        // Part I Writing
    private String examQuestionIdsPartIIA;      // Part II Listening Section A
    private String examQuestionIdsPartIIB;      // Part II Listening Section B
    private String examQuestionIdsPartIIC;      // Part II Listening Section C
    private String examQuestionIdsPartIIIA;     // Part III Reading Section A
    private String examQuestionIdsPartIIIB;     // Part III Reading Section B
    private String examQuestionIdsPartIIIC;     // Part III Reading Section C
    private String examQuestionIdsPartIV;       // Part IV Translation
    private Integer examScore;
    private Integer examScorePartI;
    private Integer examScorePartIIA;
    private Integer examScorePartIIB;
    private Integer examScorePartIIC;
    private Integer examScorePartIIIA;
    private Integer examScorePartIIIB;
    private Integer examScorePartIIIC;
    private Integer examScorePartIV;
    private String examCreatorId;
    private Integer examTimeLimit;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date examStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date examEndDate;
    /**
     * 创建时间, 设计表时设置了自动插入当前时间，无需在Java代码中设置了
     */

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间，设计表时设置了自动插入当前时间，无需在Java代码中设置了。
     * 同时@DynamicUpdate注解可以时间当数据库数据变化时自动更新，无需人工维护
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
