/***********************************************************
 * @Description : 角色的数据库操作类
 ***********************************************************/
package lsgwr.exam.repository;

import lsgwr.exam.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
