package ga.leeda.map.keywordhistory.ui;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class KeywordHistoryResponse {
    private String keyword;
    private Date date;
}
