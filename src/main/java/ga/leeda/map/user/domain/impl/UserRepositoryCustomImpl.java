package ga.leeda.map.user.domain.impl;

import ga.leeda.map.user.domain.User;
import ga.leeda.map.user.domain.UserRepositoryCustom;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Component
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final EntityManager em;

    public UserRepositoryCustomImpl(final EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<User> findByEmail(final String email) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

        Root<User> user = criteriaQuery.from(User.class);
        Predicate emailEqual = criteriaBuilder.equal(user.get("email"), email);

        criteriaQuery.select(user).where(emailEqual);

        TypedQuery<User> query = em.createQuery(criteriaQuery);
        return query.getResultList().isEmpty() ? Optional.empty() : Optional.of(query.getSingleResult());
    }
}
