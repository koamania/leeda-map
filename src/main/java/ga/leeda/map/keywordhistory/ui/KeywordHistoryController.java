package ga.leeda.map.keywordhistory.ui;

import ga.leeda.map.common.SessionManager;
import ga.leeda.map.interceptor.annotations.LoginRequired;
import ga.leeda.map.keywordhistory.application.service.KeywordHistoryService;
import ga.leeda.map.keywordhistory.domain.KeywordHistory;
import ga.leeda.map.user.application.service.UserService;
import ga.leeda.map.user.application.service.exceptions.NotFoundUser;
import ga.leeda.map.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/map/search/keyword/history")
@LoginRequired(handler = LoginRequired.ErrorHandler.RESPONSE_JSON)
public class KeywordHistoryController {
    private final UserService userService;
    private final KeywordHistoryService historyService;

    public KeywordHistoryController(final UserService userService, final KeywordHistoryService historyService) {
        this.userService = userService;
        this.historyService = historyService;
    }

    @GetMapping
    public ResponseEntity<List<KeywordHistoryResponse>> getKeywordHistory(Pageable pageable) {
        int userId = SessionManager.get(SessionManager.SessionKey.LOGIN_INFO);
        Optional<User> userOptional = userService.findUser(userId);

        if (!userOptional.isPresent()) {
            throw NotFoundUser.with(HttpStatus.INTERNAL_SERVER_ERROR, "not found user info");
        }
        List<KeywordHistory> historyList = historyService.getKeywordHistory(userOptional.get(), pageable);

        List<KeywordHistoryResponse> responseList = historyList.stream()
                .map(keywordHistory -> new KeywordHistoryResponse(keywordHistory.getKeyword().getKeyword(), keywordHistory.getCreatedDate()))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(responseList);
    }

}
