package ga.leeda.map.user.domain;

import java.util.Optional;

public interface UserRepositoryCustom {
    Optional<User> findByEmail(String email);
}
