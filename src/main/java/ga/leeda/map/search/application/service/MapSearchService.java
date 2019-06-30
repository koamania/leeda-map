package ga.leeda.map.search.application.service;

import ga.leeda.map.search.domain.MapInfomation;
import ga.leeda.map.search.domain.MapSearchParameter;
import ga.leeda.map.user.domain.User;

public interface MapSearchService {
    MapInfomation search(final User user, MapSearchParameter parameter);
}
