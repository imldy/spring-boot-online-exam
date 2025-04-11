/***********************************************************
 * @Description : 考试得分级别
 ***********************************************************/
package lsgwr.exam.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class ExamRecordLevel {
    @Id
    @GeneratedValue
    private Integer examRecordLevelId;
    private String examRecordLevelName;
    private String examRecordLevelDescription;
}
