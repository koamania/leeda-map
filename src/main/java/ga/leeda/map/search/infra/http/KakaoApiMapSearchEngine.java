package ga.leeda.map.search.infra.http;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ga.leeda.map.search.domain.MapSearchEngine;
import ga.leeda.map.search.domain.MapSearchParameter;
import ga.leeda.map.search.domain.MapSearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component
public class KakaoApiMapSearchEngine implements MapSearchEngine {

    private static final String AUTH_TYPE = "KakaoAK";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Value("${map-search.kakao.host}")
    private String kakaoApiHost;

    @Value("${map-search.kakao.api-key}")
    private String kakaoApiKey;

    private final RestTemplate restTemplate;

    @Autowired
    public KakaoApiMapSearchEngine(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public MapSearchResult search(final MapSearchParameter parameter) {
        JsonNode node = callSearchApi(parameter);

        //noinspection unchecked
        return KakaoSearchMapSearchResult.from(
                MAPPER.convertValue(node, Map.class)
        );
    }

    private JsonNode callSearchApi(final MapSearchParameter parameter) {
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.AUTHORIZATION, AUTH_TYPE + " " + kakaoApiKey);
        header.setContentType(MediaType.APPLICATION_JSON);


        String path = "/v2/local/search/keyword.json";
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromPath(path)
                .scheme("https")
                .host(kakaoApiHost)
                .queryParam("query", parameter.getQuery())
                .queryParam("size", parameter.getSize())
                .queryParam("page", parameter.getPage());

        if (parameter.getX() != 0) {
            builder.queryParam("x", parameter.getX());
        }

        if (parameter.getY() != 0) {
            builder.queryParam("y", parameter.getY());
        }

        ResponseEntity<JsonNode> response = restTemplate.exchange(builder.build().toUriString(), HttpMethod.GET, new HttpEntity(header), JsonNode.class, parameter);

        return response.getBody();
    }
}
