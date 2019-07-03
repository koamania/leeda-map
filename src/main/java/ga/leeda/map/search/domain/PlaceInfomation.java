package ga.leeda.map.search.domain;

/**
 * 검색한 장소에 대한 정보를 추상화
 */
public interface PlaceInfomation {
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
