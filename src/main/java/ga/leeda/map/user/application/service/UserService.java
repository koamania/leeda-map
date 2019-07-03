package ga.leeda.map.user.application.service;

import ga.leeda.map.user.domain.User;

import java.util.Optional;

/**
 * 사용자 정보 관리를 위한 facade 클래스
 */
public interface UserService {

    void insertNewUser(String email, String password);

    Optional<User> findUser(int userId);

    void login(LoginParam param);
}
