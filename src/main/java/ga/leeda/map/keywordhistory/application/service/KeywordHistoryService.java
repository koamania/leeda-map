package ga.leeda.map.keywordhistory.application.service;

import ga.leeda.map.keywordhistory.domain.KeywordHistory;
import ga.leeda.map.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 키워드 히스토리 관련 처리를 위한 facade
 */
public interface KeywordHistoryService {
    /**
     * 히스토리 정보를 추가한다.
     *  @param user          대상 유저
     * @param keywordString 추가할 키워드
     */
    void addHistory(User user, String keywordString);

    /**
     * 키워드 히스토리를 가져온다.
     * pagination 되어 있음
     *
     * @param user     사용자
     * @param pageable pagination 정보
     * @return Pagenation된 KeywordHistory
     */
    Page<KeywordHistory> getKeywordHistory(User user, Pageable pageable);
}
