package ga.leeda.map.keyword.ui;

import ga.leeda.map.keyword.application.service.KeywordService;
import ga.leeda.map.keyword.domain.Keyword;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 키워드 관련 정보를 가져온다.
 */
@RestController
@RequestMapping("/api/map/search/keyword")
public class KeywordController {
    private final KeywordService keywordService;

    public KeywordController(final KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    /**
     * 인기 검색어를 가져오는 api
     *
     * @param resultCount
     * @return
     */
    @GetMapping("/top")
    public List<Keyword> getTopRankKeyword(@RequestParam(required = false, value = "result_count", defaultValue = "5") int resultCount) {
        return this.keywordService.getTopRankKeywordList(resultCount);
    }
}
