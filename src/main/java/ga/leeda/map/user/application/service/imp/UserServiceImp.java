package ga.leeda.map.user.application.service.imp;

import ga.leeda.map.common.SessionManager;
import ga.leeda.map.user.application.service.LoginParam;
import ga.leeda.map.user.application.service.UserService;
import ga.leeda.map.user.application.service.exceptions.AlreadyJoinedUser;
import ga.leeda.map.user.application.service.exceptions.InvalidLoginParam;
import ga.leeda.map.user.application.service.exceptions.NotFoundUser;
import ga.leeda.map.user.domain.User;
import ga.leeda.map.user.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    public UserServiceImp(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void insertNewUser(@NotNull @Email String email, @NotNull String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new AlreadyJoinedUser(email + " user already joined");
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(PasswordEncryptor.encrypt(password));

        userRepository.save(newUser);
    }

    @Override
    public Optional<User> findUser(final int userId) {
        return userRepository.findById(userId);
    }

    @Override
    public void login(final LoginParam param) {
        if (SessionManager.get(SessionManager.SessionKey.LOGIN_INFO) != null) {
            return;
        }

        Optional<User> userOptional = userRepository.findByEmail(param.getEmail());

        User user = userOptional.orElseThrow(() -> NotFoundUser.with("not found user"));

        boolean validate = PasswordEncryptor.validatePassword(param.getPassword(), user.getPassword());
        if (validate) {
            SessionManager.set(SessionManager.SessionKey.LOGIN_INFO, user.getId());
        } else {
            throw new InvalidLoginParam();
        }
    }
}