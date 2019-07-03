package ga.leeda.map.keywordhistory.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 키워드 엔티티를 관리하기 위한 repository
 */
@Repository
public interface KeywordHistoryRepository extends JpaRepository<KeywordHistory, Integer>, KeywordHistoryRepositoryCustom {
}
