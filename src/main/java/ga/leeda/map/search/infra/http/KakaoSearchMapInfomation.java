package ga.leeda.map.search.infra.http;

import ga.leeda.map.search.domain.MapInfomation;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
public class KakaoSearchMapInfomation implements MapInfomation {

    private int totalCount = 0;
    private List<Map<String, Object>> infomation = Collections.emptyList();

    @Override
    public List<Map<String, Object>> getInfomation() {
        return this.infomation;
    }

    static KakaoSearchMapInfomation from(Map<String, Object> json) {
        KakaoSearchMapInfomation info = new KakaoSearchMapInfomation();
        //noinspection unchecked
        Map<String, Object> meta = (Map<String, Object>) json.get("meta");
        Object totalCount = meta.get("total_count");
        if (totalCount == null) {
            return info;
        }

        info.totalCount = Integer.parseInt(String.valueOf(totalCount));

        if (info.totalCount == 0) {
            return info;
        }

        //noinspection unchecked
        info.infomation = (List<Map<String, Object>>) json.get("documents");

        return info;
    }
}
