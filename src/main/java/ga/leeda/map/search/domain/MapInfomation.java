package ga.leeda.map.search.domain;

import java.util.List;
import java.util.Map;

public interface MapInfomation {
    int getTotalCount();

    List<Map<String, Object>> getInfomation();
}
