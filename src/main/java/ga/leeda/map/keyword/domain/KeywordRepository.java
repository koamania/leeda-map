package ga.leeda.map.keyword.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, Integer>, KeywordRepositoryCustom {

}
