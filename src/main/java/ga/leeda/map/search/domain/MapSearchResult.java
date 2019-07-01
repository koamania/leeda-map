package ga.leeda.map.search.domain;

import java.util.List;

public interface MapSearchResult {
    int getTotalCount();

    List<MapInfomation> getInfomation();
}
