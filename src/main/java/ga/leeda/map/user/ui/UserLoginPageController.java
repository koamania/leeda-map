package ga.leeda.map.user.ui;

import ga.leeda.map.common.SessionManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 사용자 로그인페이지 controller
 */
@RequestMapping
@Controller
public class UserLoginPageController {

    @GetMapping({"", "/"})
    public String mainPage() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        if (SessionManager.get(SessionManager.SessionKey.LOGIN_INFO) != null) {
            return "redirect:/map/search";
        }

        return "/login/login.html";
    }
}
