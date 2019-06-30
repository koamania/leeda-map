package ga.leeda.map.keywordhistory.application.service.impl;

import ga.leeda.map.keywordhistory.application.service.KeywordHistoryService;
import ga.leeda.map.keywordhistory.domain.KeywordHistory;
import ga.leeda.map.keywordhistory.domain.KeywordRepository;
import ga.leeda.map.user.domain.User;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KeywordHistoryServiceImpl implements KeywordHistoryService {

    private KeywordRepository repository;

    public KeywordHistoryServiceImpl(final KeywordRepository repository) {
        this.repository = repository;
    }

    @Override
    @Async
    @Transactional
    public void addHistory(final User user, final String keyword) {
        KeywordHistory keywordHistory = new KeywordHistory();
        keywordHistory.setUser(user);
        keywordHistory.setKeyword(keyword);
        repository.save(keywordHistory);
    }
}
