package ga.leeda.map.search.application.service.impl;

import ga.leeda.map.keywordhistory.application.service.KeywordHistoryService;
import ga.leeda.map.search.application.service.MapSearchService;
import ga.leeda.map.search.domain.MapSearchEngine;
import ga.leeda.map.search.domain.MapSearchParameter;
import ga.leeda.map.search.domain.MapSearchResult;
import ga.leeda.map.user.domain.User;
import org.springframework.stereotype.Service;

@Service
public class MapSearchServiceImpl implements MapSearchService {
    private final MapSearchEngine searchEngine;
    private final KeywordHistoryService historyService;

    public MapSearchServiceImpl(final MapSearchEngine searchEngine, final KeywordHistoryService historyService) {
        this.searchEngine = searchEngine;
        this.historyService = historyService;
    }

    @Override
    public MapSearchResult search(final User user, final MapSearchParameter parameter) {
        historyService.addHistory(user, parameter.getQuery());
        return searchEngine.search(parameter);
    }
}
