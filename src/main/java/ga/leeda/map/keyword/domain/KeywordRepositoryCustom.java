package ga.leeda.map.keyword.domain;

import java.util.List;
import java.util.Optional;

/**
 * criteria api 사용을 위한 커스텀 클래스
 */
public interface KeywordRepositoryCustom {
    /**
     * keyword 정보로부터 엔티티를 검색해온다.
     *
     * @param keywordString
     * @return
     */
    Optional<Keyword> findByKeyword(String keywordString);

    /**
     * 키워드 정보를 검색, 없으면 만들어서 주자
     *
     * @param keywordString
     * @return
     */
    Keyword findOrCreate(String keywordString);

    /**
     * 검색어 hitCount가 높은 resultcount개만큼 반환
     * @param resultCount 가져올 column 갯수
     * @return 검색된 키워드 리스트
     */
    List<Keyword> findTopRankKeyword(int resultCount);
}
