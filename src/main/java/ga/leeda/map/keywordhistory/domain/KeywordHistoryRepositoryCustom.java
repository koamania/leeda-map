package ga.leeda.map.keywordhistory.domain;

import ga.leeda.map.keyword.domain.Keyword;
import ga.leeda.map.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Criteria api 사용을 위한 custom repository
 */
public interface KeywordHistoryRepositoryCustom {
    /**
     * 특정 사용자의 keyword에 대한 히스토리를 가져온다.
     *
     * @param user
     * @param keywordString
     * @return
     */
    Optional<KeywordHistory> findByKeyword(User user, Keyword keywordString);

    /**
     * 특정 사용자의 키워드 히스토리르 pagination 형태로 가져온다.
     *
     * @param user
     * @param pageable
     * @return
     */
    Page<KeywordHistory> findByUser(User user, Pageable pageable);
}
