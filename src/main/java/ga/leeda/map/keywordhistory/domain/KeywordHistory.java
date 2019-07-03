package ga.leeda.map.keywordhistory.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ga.leeda.map.keyword.domain.Keyword;
import ga.leeda.map.user.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * 키워드 히스토리 엔티티
 * {@link KeywordHistory} 엔티티와 {@link User} 엔티티와는 ManyToOne 관게를 가짐
 * {@link KeywordHistory} 엔티티와 {@link Keyword} 엔티티와는 ManyToOne 관게를 가짐
 * <p>
 * json으로 반환 시 User entity는 보안을 위해 JsonIgnore
 */
@Entity
@Data
@NoArgsConstructor
public class KeywordHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne(targetEntity = Keyword.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "keyword_id")
    private Keyword keyword;

    @UpdateTimestamp
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
}
