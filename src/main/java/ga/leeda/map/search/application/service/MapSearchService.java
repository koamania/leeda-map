package ga.leeda.map.search.application.service;

import ga.leeda.map.search.domain.MapSearchParameter;
import ga.leeda.map.search.domain.MapSearchResult;
import ga.leeda.map.user.domain.User;

/**
 * 장소 검색에 대한 기능을 담당하는 facade
 */
public interface MapSearchService {
    MapSearchResult search(final User user, MapSearchParameter parameter);
}
