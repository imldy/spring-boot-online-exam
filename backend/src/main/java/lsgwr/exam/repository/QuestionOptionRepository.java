/***********************************************************
 * @Description : 
 ***********************************************************/
package lsgwr.exam.repository;

import lsgwr.exam.entity.QuestionOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionOptionRepository extends JpaRepository<QuestionOption, String> {
}
