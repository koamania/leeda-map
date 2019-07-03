package ga.leeda.map.user.domain;

import java.util.Optional;

/**
 * Criteria API 를 활용하기 위한 custom repository
 */
public interface UserRepositoryCustom {
    Optional<User> findByEmail(String email);
}
