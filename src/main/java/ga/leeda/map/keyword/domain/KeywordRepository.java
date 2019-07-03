package ga.leeda.map.keyword.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 키워드 repository 클래스
 */
public interface KeywordRepository extends JpaRepository<Keyword, Integer>, KeywordRepositoryCustom {

}
