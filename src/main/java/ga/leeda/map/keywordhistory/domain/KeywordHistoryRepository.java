package ga.leeda.map.keywordhistory.domain;

import ga.leeda.map.keyword.domain.Keyword;
import ga.leeda.map.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 키워드 엔티티를 관리하기 위한 repository
 */
@Repository
public interface KeywordHistoryRepository extends JpaRepository<KeywordHistory, Integer> {
    Optional<KeywordHistory> findByUserAndKeyword(User user, Keyword keyword);

    Page<KeywordHistory> findByUser(User user, Pageable pageable);
}
