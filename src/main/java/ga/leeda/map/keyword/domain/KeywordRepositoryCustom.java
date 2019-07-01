package ga.leeda.map.keyword.domain;

import java.util.Optional;

public interface KeywordRepositoryCustom {
    Optional<Keyword> findByKeyword(String keywordString);

    Keyword findOrCreate(String keywordString);
}
