package ga.leeda.map.search.infra.http;

import ga.leeda.map.search.domain.MapInfomation;
import ga.leeda.map.search.domain.MapSearchResult;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Getter
class KakaoSearchMapSearchResult implements MapSearchResult {

    private int totalCount = 0;
    private List<MapInfomation> infomation = new LinkedList<>();

    static KakaoSearchMapSearchResult from(Map<String, Object> json) {
        KakaoSearchMapSearchResult info = new KakaoSearchMapSearchResult();
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
        List<Map<String, String>> documents = (List<Map<String, String>>) json.get("documents");

        for (Map<String, String> document : documents) {
            MapInfomation mapInfomation = KakaoMapInfomation.builder()
                    .placeName(document.getOrDefault("place_name", ""))
                    .addressName(document.getOrDefault("address_name", ""))
                    .roadAddressName(document.getOrDefault("road_address_name", ""))
                    .x(Float.parseFloat(document.getOrDefault("x", "0")))
                    .y(Float.parseFloat(document.getOrDefault("y", "0")))
                    .phone(document.getOrDefault("phone", ""))
                    .mapUrl(document.getOrDefault("place_url", ""))
                    .build();

            info.infomation.add(mapInfomation);
        }

        return info;
    }
}
