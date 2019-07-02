package ga.leeda.map.keyword.domain.application.service;

import ga.leeda.map.keyword.domain.Keyword;

import java.util.List;

public interface KeywordService {
    List<Keyword> getTopRankKeywordList(int resultCount);
}
