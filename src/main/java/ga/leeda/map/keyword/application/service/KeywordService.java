package ga.leeda.map.keyword.application.service;

import ga.leeda.map.keyword.domain.Keyword;

import java.util.List;

/**
 * 키워드 도메인 관련 처리를 위한 facade
 */
public interface KeywordService {
    /**
     * top rank키워드를 가져온다.
     *
     * @param resultCount 가져올 갯수
     * @return 키워드 갯수
     */
    List<Keyword> getTopRankKeywordList(int resultCount);
}
