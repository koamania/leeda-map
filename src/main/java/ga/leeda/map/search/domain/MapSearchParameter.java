package ga.leeda.map.search.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapSearchParameter {
    @NotEmpty
    private String query;
    @Positive
    private int page = 1;
    @Min(1)
    @Max(15)
    private int size = 10;
}
