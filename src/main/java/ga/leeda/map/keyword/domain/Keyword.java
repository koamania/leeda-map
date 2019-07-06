package ga.leeda.map.keyword.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Keyword 엔티티
 * Keyword의 사용량 확인의 용이성 및 확장성을 위해서 별도의 엔티티로 분리
 */
@Entity
@Data
@NoArgsConstructor
public class Keyword implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique = true)
    @NotNull
    private String keyword;
    @NotNull
    private int hitCount = 0;

    public void increaseHitCount() {
        this.hitCount += 1;
    }
}
