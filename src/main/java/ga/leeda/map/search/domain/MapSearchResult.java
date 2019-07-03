package ga.leeda.map.search.domain;

import java.util.List;

/**
 * 장소 검색에 메타정보 및 장소 정보를 추상화
 */
public interface MapSearchResult {
    int getTotalCount();

    int getResultCount();

    boolean isEnd();

    List<PlaceInfomation> getInfomation();
}
