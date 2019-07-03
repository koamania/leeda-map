package ga.leeda.map.keywordhistory.application.service;

import ga.leeda.map.keywordhistory.domain.KeywordHistory;
import ga.leeda.map.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface KeywordHistoryService {
    void addHistory(User user, String keywordString);

    Page<KeywordHistory> getKeywordHistory(User user, Pageable pageable);
}
