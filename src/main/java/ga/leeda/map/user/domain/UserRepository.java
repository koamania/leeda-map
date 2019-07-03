package ga.leeda.map.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 사용자 엔티티를 관리하기 위한 repository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryCustom {
}
