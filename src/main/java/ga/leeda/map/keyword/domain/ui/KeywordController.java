package ga.leeda.map.keyword.domain.ui;

import ga.leeda.map.keyword.domain.Keyword;
import ga.leeda.map.keyword.domain.application.service.KeywordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/map/search/keyword")
public class KeywordController {
    private final KeywordService keywordService;

    public KeywordController(final KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    @GetMapping("/top")
    public List<Keyword> getTopRankKeyword(@RequestParam(required = false, value = "result_count", defaultValue = "5") int resultCount) {
        return this.keywordService.getTopRankKeywordList(resultCount);
    }
}
