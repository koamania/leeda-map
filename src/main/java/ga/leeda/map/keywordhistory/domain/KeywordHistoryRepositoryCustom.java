package ga.leeda.map.keywordhistory.domain;

import ga.leeda.map.keyword.domain.Keyword;
import ga.leeda.map.user.domain.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface KeywordHistoryRepositoryCustom {
    Optional<KeywordHistory> findByKeyword(User user, Keyword keywordString);

    List<KeywordHistory> findByUser(User user, Pageable pageable);
}
