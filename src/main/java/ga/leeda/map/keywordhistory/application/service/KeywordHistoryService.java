package ga.leeda.map.keywordhistory.application.service;

import ga.leeda.map.user.domain.User;

public interface KeywordHistoryService {
    void addHistory(User user, String keywordString);
}
