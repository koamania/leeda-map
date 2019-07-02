package ga.leeda.map.keyword.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class Keyword {
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
