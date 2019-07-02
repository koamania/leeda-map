package ga.leeda.map.keyword.domain;

import java.util.List;
import java.util.Optional;

public interface KeywordRepositoryCustom {
    Optional<Keyword> findByKeyword(String keywordString);

    Keyword findOrCreate(String keywordString);

    List<Keyword> findTopRankKeyword(int resultCount);
}
