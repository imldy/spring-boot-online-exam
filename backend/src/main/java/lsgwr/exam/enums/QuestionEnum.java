/***********************************************************
 * @Description : 问题类型的的枚举
 ***********************************************************/
package lsgwr.exam.enums;

import lombok.Getter;

@Getter
public enum QuestionEnum {

    /**
     * 问题类型
     */
    RADIO(1, "单选题"),
    CHECK(2, "多选题"),
    JUDGE(3, "判断题"),
    PART_I(411, "写作题"),
    PART_II_A(511, "听力A部分"),
    PART_II_B(521, "听力B部分"), 
    PART_II_C(531, "听力C部分"),
    PART_III_A(611, "阅读A部分"),
    PART_III_B(621, "阅读B部分"),
    PART_III_C(631, "阅读C部分"),
    PART_IV(711, "翻译题");


    QuestionEnum(Integer id, String role) {
        this.id = id;
        this.role = role;
    }

    private Integer id;
    private String role;
}
