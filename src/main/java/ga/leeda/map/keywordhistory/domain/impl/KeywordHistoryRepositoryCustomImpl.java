package ga.leeda.map.keywordhistory.domain.impl;

import ga.leeda.map.keyword.domain.Keyword;
import ga.leeda.map.keywordhistory.domain.KeywordHistory;
import ga.leeda.map.keywordhistory.domain.KeywordHistoryRepositoryCustom;
import ga.leeda.map.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Component
public class KeywordHistoryRepositoryCustomImpl implements KeywordHistoryRepositoryCustom {

    private final EntityManager em;

    public KeywordHistoryRepositoryCustomImpl(final EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<KeywordHistory> findByKeyword(final User user, final Keyword keyword) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<KeywordHistory> criteriaQuery = criteriaBuilder.createQuery(KeywordHistory.class);

        Root<KeywordHistory> keywordHistoryRoot = criteriaQuery.from(KeywordHistory.class);
        Predicate keywordEqual = criteriaBuilder.equal(keywordHistoryRoot.get("keyword"), keyword);
        Predicate userEqual = criteriaBuilder.equal(keywordHistoryRoot.get("user"), user);

        criteriaQuery.select(keywordHistoryRoot).where(keywordEqual, userEqual);

        TypedQuery<KeywordHistory> query = em.createQuery(criteriaQuery);
        return query.getResultList().isEmpty() ? Optional.empty() : Optional.of(query.getSingleResult());
    }

    @Override
    public List<KeywordHistory> findByUser(final User user, final Pageable pageable) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<KeywordHistory> criteriaQuery = criteriaBuilder.createQuery(KeywordHistory.class);

        Root<KeywordHistory> keywordHistoryRoot = criteriaQuery.from(KeywordHistory.class);

        Predicate userEqual = criteriaBuilder.equal(keywordHistoryRoot.get("user"), user);

        criteriaQuery.select(keywordHistoryRoot)
                .where(userEqual)
                .orderBy(criteriaBuilder.desc(keywordHistoryRoot.get("createdDate")));

        TypedQuery<KeywordHistory> query = em.createQuery(criteriaQuery);

        int pageNumber = pageable.getPageNumber() <= 0 ? 1 : pageable.getPageNumber();
        int pageSize = pageable.getPageSize() <= 0 ? 1 : pageable.getPageSize();

        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }
}
