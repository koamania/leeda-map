package ga.leeda.map.user.application.service;

import ga.leeda.map.user.domain.User;

import java.util.Optional;

public interface UserService {

    void insertNewUser(String email, String password);

    Optional<User> findUser(int userId);
    void login(LoginParam param);
}
