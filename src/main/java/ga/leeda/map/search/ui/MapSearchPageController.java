package ga.leeda.map.search.ui;

import ga.leeda.map.interceptor.annotations.LoginRequired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 장소 검색 페이지에 대한 컨트롤러
 */
@Controller
@RequestMapping("/map/search")
@LoginRequired
public class MapSearchPageController {

    @GetMapping
    public String mapSearchPage() {
        return "/map/map.html";
    }
}
