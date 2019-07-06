package ga.leeda.map.keywordhistory.application.service.impl;

import ga.leeda.map.keyword.application.service.KeywordService;
import ga.leeda.map.keyword.domain.Keyword;
import ga.leeda.map.keywordhistory.application.service.KeywordHistoryService;
import ga.leeda.map.keywordhistory.domain.KeywordHistory;
import ga.leeda.map.keywordhistory.domain.KeywordHistoryRepository;
import ga.leeda.map.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class KeywordHistoryServiceImpl implements KeywordHistoryService {
    private KeywordService keywordService;
    private KeywordHistoryRepository historyRepository;

    public KeywordHistoryServiceImpl(final KeywordService keywordService, final KeywordHistoryRepository historyRepository) {
        this.keywordService = keywordService;
        this.historyRepository = historyRepository;
    }

    @Override
    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addHistory(final User user, final String keywordString) {
        Keyword keyword = keywordService.findOrCreate(keywordString);
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

    public Page<KeywordHistory> getKeywordHistory(User user, Pageable pageable) {
        return historyRepository.findByUser(user, pageable);
    }
}
