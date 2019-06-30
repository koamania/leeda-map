package ga.leeda.map.user.interfaces.service.imp;

import ga.leeda.map.common.SessionManager;
import ga.leeda.map.user.domain.User;
import ga.leeda.map.user.domain.UserRepository;
import ga.leeda.map.user.interfaces.service.LoginParam;
import ga.leeda.map.user.interfaces.service.UserService;
import ga.leeda.map.user.interfaces.service.exceptions.InvalidLoginParam;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    public UserServiceImp(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void insertNewUser(@NotNull @Email String email, @NotNull String password) {

        User user = new User();
        user.setEmail(email);
        user.setPassword(PasswordEncryptor.encrypt(password));

        userRepository.save(user);
    }

    @Override
    public void login(final LoginParam param) {
        if (SessionManager.get(SessionManager.SessionKey.LOGIN_INFO) != null) {
            return;
        }

        User user = userRepository.findByEmail(param.getEmail());

        boolean validate = PasswordEncryptor.validatePassword(param.getPassword(), user.getPassword());
        if (validate) {
            SessionManager.set(SessionManager.SessionKey.LOGIN_INFO, user.getId());
        } else {
            throw new InvalidLoginParam();
        }
    }
}