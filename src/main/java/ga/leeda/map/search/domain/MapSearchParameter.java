package ga.leeda.map.search.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

/**
 * 장소 검색에 필요한 파라미터
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapSearchParameter {
    @NotEmpty
    private String query;
    @Positive
    @Min(1)
    @Max(45)
    private int page = 1;
    @Min(1)
    @Max(15)
    private int size = 15;
    private float x = 0;
    private float y = 0;
}
