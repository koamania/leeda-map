package ga.leeda.map.search.domain;

import java.io.Serializable;

/**
 * 검색한 장소에 대한 정보를 추상화
 */
public interface PlaceInfomation extends Serializable {
    String getId();

    String getPlaceName();

    String getAddressName();

    String getRoadAddressName();

    float getX();

    float getY();

    String getPlaceUrl();

    String getMapUrl();

    String getPhone();
}
