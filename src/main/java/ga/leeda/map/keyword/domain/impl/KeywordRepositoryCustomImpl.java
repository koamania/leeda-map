package ga.leeda.map.keyword.domain.impl;

import ga.leeda.map.keyword.domain.Keyword;
import ga.leeda.map.keyword.domain.KeywordRepositoryCustom;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Component
public class KeywordRepositoryCustomImpl implements KeywordRepositoryCustom {

    private final EntityManager em;

    public KeywordRepositoryCustomImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Keyword> findByKeyword(final String keywordString) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Keyword> criteriaQuery = criteriaBuilder.createQuery(Keyword.class);

        Root<Keyword> keyword = criteriaQuery.from(Keyword.class);
        Predicate keywordEqual = criteriaBuilder.equal(keyword.get("keyword"), keywordString);

        criteriaQuery.select(keyword).where(keywordEqual);

        TypedQuery<Keyword> query = em.createQuery(criteriaQuery);
        return query.getResultList().isEmpty() ? Optional.empty() : Optional.of(query.getSingleResult());
    }

    @Override
    public Keyword findOrCreate(final String keywordString) {
        return this.findByKeyword(keywordString).orElseGet(() -> {

            Keyword keyword = new Keyword();
            keyword.setKeyword(keywordString);

            this.em.persist(keyword);

            return keyword;
        });
    }
}
