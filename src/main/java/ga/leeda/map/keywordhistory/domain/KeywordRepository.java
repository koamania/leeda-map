package ga.leeda.map.keywordhistory.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordRepository extends JpaRepository<KeywordHistory, Integer> {
}
