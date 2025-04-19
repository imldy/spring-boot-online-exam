/***********************************************************
 * @Description : 考试详情的实体类
 ***********************************************************/
package lsgwr.exam.vo;

import lsgwr.exam.entity.Exam;
import lombok.Data;

@Data
public class ExamDetailVo {
    /**
     * 考试的基本信息对象
     */
    private Exam exam;

    /**
     * 单选题的id数组
     */
    private String[] radioIds;

    /**
     * 多选题的id数组
     */
    private String[] checkIds;

    /**
     * 判断题的id数组
     */
    private String[] judgeIds;

    /**
     * Part I题型的id数组
     */
    private String[] partIIds;

    /**
     * Part IIA题型的id数组
     */
    private String[] partIIAIds;

    /**
     * Part IIB题型的id数组
     */
    private String[] partIIBIds;

    /**
     * Part IIC题型的id数组
     */
    private String[] partIICIds;

    /**
     * Part IIIA题型的id数组
     */
    private String[] partIIIAIds;

    /**
     * Part IIIB题型的id数组
     */
    private String[] partIIIBIds;

    /**
     * Part IIIC题型的id数组
     */
    private String[] partIIICIds;

    /**
     * Part IV题型的id数组
     */
    private String[] partIVIds;

}
