package ga.leeda.map.user.ui;

import ga.leeda.map.user.application.service.LoginParam;
import ga.leeda.map.user.application.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserLoginApiController {

    private final UserService userService;

    public UserLoginApiController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid LoginParam param) {
        this.userService.login(param);
        return ResponseEntity.ok().build();
    }
}
