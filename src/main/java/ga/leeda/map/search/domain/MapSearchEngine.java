package ga.leeda.map.search.domain;

/**
 * 장소 검색에 기능을 추상화
 */
public interface MapSearchEngine {
    MapSearchResult search(MapSearchParameter parameter);
}
