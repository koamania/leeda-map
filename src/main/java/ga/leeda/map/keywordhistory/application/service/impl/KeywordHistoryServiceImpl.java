package ga.leeda.map.keywordhistory.application.service.impl;

import ga.leeda.map.keyword.domain.Keyword;
import ga.leeda.map.keyword.domain.KeywordRepository;
import ga.leeda.map.keywordhistory.application.service.KeywordHistoryService;
import ga.leeda.map.keywordhistory.domain.KeywordHistory;
import ga.leeda.map.keywordhistory.domain.KeywordHistoryRepository;
import ga.leeda.map.user.domain.User;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KeywordHistoryServiceImpl implements KeywordHistoryService {

    private KeywordRepository keywordRepository;
    private KeywordHistoryRepository historyRepository;

    public KeywordHistoryServiceImpl(final KeywordRepository keywordRepository, final KeywordHistoryRepository historyRepository) {
        this.keywordRepository = keywordRepository;
        this.historyRepository = historyRepository;
    }

    @Override
    @Async
    @Transactional
    public void addHistory(final User user, final String keywordString) {
        Keyword keyword = keywordRepository.findOrCreate(keywordString);

        KeywordHistory keywordHistory = new KeywordHistory();
        keywordHistory.setUser(user);
        keywordHistory.setKeyword(keyword);

        historyRepository.save(keywordHistory);
    }
}
