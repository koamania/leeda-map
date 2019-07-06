package ga.leeda.map.search.infra.http;

import ga.leeda.map.search.domain.PlaceInfomation;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
class KakaoPlaceInfomation implements PlaceInfomation {
    private static final long serialVersionUID = 3377231185991229059L;
    private String id;
    private String placeName;
    private String addressName;
    private String roadAddressName;
    private float x;
    private float y;
    private String placeUrl;
    private String mapUrl;
    private String phone;
}
