package ga.leeda.map.user.interfaces.controller;

import ga.leeda.map.user.interfaces.service.LoginParam;
import ga.leeda.map.user.interfaces.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController("/api/user")
public class UserLoginController {

    private final UserService userService;

    public UserLoginController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String login(LoginParam param) {
        this.userService.login(param);
        return "asd";
    }
}
