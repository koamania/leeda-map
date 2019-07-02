package ga.leeda.map.keywordhistory.application.service;

import ga.leeda.map.keywordhistory.domain.KeywordHistory;
import ga.leeda.map.user.domain.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface KeywordHistoryService {
    void addHistory(User user, String keywordString);

    List<KeywordHistory> getKeywordHistory(User user, Pageable pageable);
}
