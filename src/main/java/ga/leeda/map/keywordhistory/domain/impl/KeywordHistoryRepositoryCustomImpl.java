package ga.leeda.map.keywordhistory.domain.impl;

import ga.leeda.map.keyword.domain.Keyword;
import ga.leeda.map.keywordhistory.domain.KeywordHistory;
import ga.leeda.map.keywordhistory.domain.KeywordHistoryRepositoryCustom;
import ga.leeda.map.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    public Page<KeywordHistory> findByUser(final User user, final Pageable pageable) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<KeywordHistory> criteriaQuery = criteriaBuilder.createQuery(KeywordHistory.class);

        Root<KeywordHistory> keywordHistoryRoot = criteriaQuery.from(KeywordHistory.class);

        Predicate userEqual = criteriaBuilder.equal(keywordHistoryRoot.get("user"), user);

        criteriaQuery.select(keywordHistoryRoot)
                .where(userEqual)
                .orderBy(criteriaBuilder.desc(keywordHistoryRoot.get("createdDate")));

        TypedQuery<KeywordHistory> query = em.createQuery(criteriaQuery);

        List<KeywordHistory> results = query.setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<KeywordHistory> keywordHistoryRootCount = countQuery.from(KeywordHistory.class);
        countQuery.select(criteriaBuilder.count(keywordHistoryRootCount)).where(userEqual);

        long count = em.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(results, pageable, count);
    }
}
