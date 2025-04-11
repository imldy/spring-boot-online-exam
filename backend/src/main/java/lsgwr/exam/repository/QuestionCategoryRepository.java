/***********************************************************
 * @Description : 
 ***********************************************************/
package lsgwr.exam.repository;

import lsgwr.exam.entity.QuestionCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionCategoryRepository extends JpaRepository<QuestionCategory, Integer> {
}
