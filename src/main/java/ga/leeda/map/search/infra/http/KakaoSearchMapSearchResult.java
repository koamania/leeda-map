package ga.leeda.map.search.infra.http;

import ga.leeda.map.search.domain.MapSearchResult;
import ga.leeda.map.search.domain.PlaceInfomation;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Getter
class KakaoSearchMapSearchResult implements MapSearchResult {

    private static final long serialVersionUID = 6395167121152791771L;
    private int totalCount = 0;
    private int resultCount = 0;
    private boolean end = false;
    private List<PlaceInfomation> infomation = new LinkedList<>();

    static KakaoSearchMapSearchResult from(Map<String, Object> json) {
        KakaoSearchMapSearchResult info = new KakaoSearchMapSearchResult();
        //noinspection unchecked
        Map<String, Object> meta = (Map<String, Object>) json.get("meta");

        info.totalCount = Integer.parseInt(String.valueOf(meta.get("total_count")));
        info.resultCount = Integer.parseInt(String.valueOf(meta.get("pageable_count")));

        if (info.totalCount == 0 || info.resultCount == 0) {
            return info;
        }

        info.end = Boolean.parseBoolean(String.valueOf(meta.get("is_end")));

        //noinspection unchecked
        List<Map<String, String>> documents = (List<Map<String, String>>) json.get("documents");

        for (Map<String, String> document : documents) {
            PlaceInfomation placeInfomation = KakaoPlaceInfomation.builder()
                    .id(document.getOrDefault("id", ""))
                    .placeName(document.getOrDefault("place_name", ""))
                    .addressName(document.getOrDefault("address_name", ""))
                    .roadAddressName(document.getOrDefault("road_address_name", ""))
                    .x(Float.parseFloat(document.getOrDefault("x", "0")))
                    .y(Float.parseFloat(document.getOrDefault("y", "0")))
                    .phone(document.getOrDefault("phone", ""))
                    .mapUrl("https://map.kakao.com/link/map/" + document.getOrDefault("id", ""))
                    .placeUrl(document.getOrDefault("place_url", ""))
                    .build();

            info.infomation.add(placeInfomation);
        }

        return info;
    }
}
