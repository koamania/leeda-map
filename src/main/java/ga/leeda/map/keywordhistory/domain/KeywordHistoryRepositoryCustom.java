package ga.leeda.map.keywordhistory.domain;

import ga.leeda.map.keyword.domain.Keyword;
import ga.leeda.map.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface KeywordHistoryRepositoryCustom {
    Optional<KeywordHistory> findByKeyword(User user, Keyword keywordString);

    Page<KeywordHistory> findByUser(User user, Pageable pageable);
}
