package ga.leeda.map.keywordhistory.application.service.impl;

import ga.leeda.map.keyword.domain.Keyword;
import ga.leeda.map.keyword.domain.KeywordRepository;
import ga.leeda.map.keywordhistory.application.service.KeywordHistoryService;
import ga.leeda.map.keywordhistory.domain.KeywordHistory;
import ga.leeda.map.keywordhistory.domain.KeywordHistoryRepository;
import ga.leeda.map.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addHistory(final User user, final String keywordString) {
        Keyword keyword = keywordRepository.findOrCreate(keywordString);
        keyword.increaseHitCount();

        KeywordHistory keywordHistory = historyRepository.findByKeyword(user, keyword)
                .orElseGet(() -> {
                    KeywordHistory newKeywordHistory = new KeywordHistory();
                    newKeywordHistory.setUser(user);
                    newKeywordHistory.setKeyword(keyword);
                    return newKeywordHistory;
                });

        keywordHistory.setCreatedDate(new Date());
        historyRepository.save(keywordHistory);
    }

    public List<KeywordHistory> getKeywordHistory(User user, Pageable pageable) {
        return historyRepository.findByUser(user, pageable);
    }
}
