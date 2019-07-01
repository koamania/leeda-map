package ga.leeda.map.search.infra.http;

import ga.leeda.map.search.domain.MapInfomation;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
class KakaoMapInfomation implements MapInfomation {
    private String placeName;
    private String addressName;
    private String roadAddressName;
    private float x;
    private float y;
    private String mapUrl;
    private String phone;
}
