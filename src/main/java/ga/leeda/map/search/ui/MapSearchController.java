package ga.leeda.map.search.ui;

import ga.leeda.map.common.SessionManager;
import ga.leeda.map.interceptor.annotations.LoginRequired;
import ga.leeda.map.search.application.service.MapSearchService;
import ga.leeda.map.search.domain.MapSearchParameter;
import ga.leeda.map.user.application.service.UserService;
import ga.leeda.map.user.application.service.exceptions.NotFoundUser;
import ga.leeda.map.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/map")
@LoginRequired
public class MapSearchController {

    private final MapSearchService searchService;
    private final UserService userService;

    @Autowired
    public MapSearchController(MapSearchService searchService, final UserService userService) {
        this.searchService = searchService;
        this.userService = userService;
    }

    @GetMapping("/search")
    public ResponseEntity searchByKeyword(@Valid MapSearchParameter parameter) {
        int userId = SessionManager.get(SessionManager.SessionKey.LOGIN_INFO);
        Optional<User> userOptional = userService.findUser(userId);

        if (!userOptional.isPresent()) {
            throw new NotFoundUser(HttpStatus.INTERNAL_SERVER_ERROR, "not found user info");
        }

        return ResponseEntity.ok().body(searchService.search(userOptional.get(), parameter));
    }
}
