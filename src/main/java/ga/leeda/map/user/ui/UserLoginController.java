package ga.leeda.map.user.ui;

import ga.leeda.map.user.application.service.LoginParam;
import ga.leeda.map.user.application.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserLoginController {

    private final UserService userService;

    public UserLoginController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET})
    public String login(LoginParam param) {
        this.userService.login(param);
        return "asd";
    }
}
