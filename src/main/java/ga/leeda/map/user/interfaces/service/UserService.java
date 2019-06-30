package ga.leeda.map.user.interfaces.service;

import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    @Transactional
    void insertNewUser(String email, String password);

    void login(LoginParam param);
}
