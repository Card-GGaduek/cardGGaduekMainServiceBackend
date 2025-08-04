package org.cardGGaduekMainService.place.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.cardGGaduekMainService.common.util.HttpClientUtil;
import org.cardGGaduekMainService.exception.CustomException;
import org.cardGGaduekMainService.exception.ErrorCode;
import org.cardGGaduekMainService.place.dto.GoogleApiRequest;
import org.cardGGaduekMainService.place.dto.GoogleApiResponse;
import org.cardGGaduekMainService.place.dto.PlaceResponse;
import org.cardGGaduekMainService.place.dto.PlaceSearchRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Configuration
@PropertySource("classpath:application-secret.properties")
public class PlaceService {

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(PlaceService.class);
    //    private final RestTemplate restTemplate;
    @Value("${google.apikey}")
    private String googleApiKey;
    private final String GOOGLE_MAPS_API_URL = "https://places.googleapis.com/v1/places:searchText";
    public PlaceResponse findPlaceByName(String textQuery, Double minLat, Double minLon, Double maxLat, Double maxLon) {

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Goog-FieldMask", "places.displayName,places.formattedAddress,places.location,places.primaryType");
        headers.put("X-Goog-Api-Key", googleApiKey);

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("textQuery", textQuery);
        bodyMap.put("languageCode", "ko");

        // Google Places API의 rectangle 기반 locationBias 정의
        Map<String, Object> locationBias = new HashMap<>();
        Map<String, Object> rectangle = new HashMap<>();

        Map<String, Object> low = new HashMap<>();
        low.put("latitude", minLat);
        low.put("longitude", minLon);

        Map<String, Object> high = new HashMap<>();
        high.put("latitude", maxLat);
        high.put("longitude", maxLon);

        rectangle.put("low", low);
        rectangle.put("high", high);
        locationBias.put("rectangle", rectangle);

        bodyMap.put("locationBias", locationBias);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String jsonRequestBody = objectMapper.writeValueAsString(bodyMap);
            String response = (String) HttpClientUtil.post(GOOGLE_MAPS_API_URL, headers, jsonRequestBody);
            log.debug("Google API 응답: {}", response);

            GoogleApiResponse googleApiResponse = objectMapper.readValue(response, GoogleApiResponse.class);

            List<PlaceResponse.PlaceDTO> places = googleApiResponse.getPlaces().stream()
                    .map(place -> new PlaceResponse.PlaceDTO(
                            place.getDisplayName().getText(),
                            place.getFormattedAddress(),
                            place.getPrimaryType(),
                            new PlaceResponse.LocationDTO(
                                    place.getLocation().getLatitude(),
                                    place.getLocation().getLongitude()
                            )
                    ))
                    .collect(Collectors.toList());

            return new PlaceResponse(places);

        } catch (Exception e) {
            log.error("Google API 호출 또는 파싱 실패", e);
            throw new CustomException(ErrorCode.PLACE_FETCH_FAIL);
        }



    }

//    public PlaceResponse searchPlace(PlaceSearchRequest request) {
//        // 검색어 유효성 검사
//        String keyword = request.getName();
//        String category = request.getCategory();
//        PlaceResponse.PlaceDTO placeDTO = new PlaceResponse.PlaceDTO();
//        if(keyword == null || keyword.trim().isEmpty() ){
//            return new PlaceResponse();
//        }
//
//        // google api 요청 본문 생성
//        GoogleApiRequest googleApiRequest = new GoogleApiRequest(request.getName(),
//                new GoogleApiRequest.LocationRestriction(
//                        new GoogleApiRequest.Rectangle(
//                                new GoogleApiRequest.LatLng(request.getMinLatitude(),request.getMinLongitude()),
//                                new GoogleApiRequest.LatLng(request.getMaxLatitude(),request.getMaxLongitude())
//
//                        )
//                )
//        );
//
//        // http 헤더 설정
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("X-Goog-Api-Key", googleApiKey);
//        headers.set("X-Goog-FieldMask", "*");
//
//        // 요청 객체 생성
//        HttpEntity<GoogleApiRequest> entity = new HttpEntity<>(googleApiRequest, headers);
//
//        // RestTemplate로 Google API 호출
//        ResponseEntity<GoogleApiResponse> responseEntity = restTemplate.exchange(
//                GOOGLE_MAPS_API_URL, HttpMethod.POST, entity, GoogleApiResponse.class);
//        GoogleApiResponse googleApiResponse = responseEntity.getBody();
//        System.out.println("🔥 Google API 응답 원문:\n" + responseEntity);
//
//        // Google 응답을 프론트엔드용 DTO로 변환
//        List<PlaceResponse.PlaceDTO> places = googleApiResponse.getPlaces().stream()
//                .map(googlePlace -> new PlaceResponse.PlaceDTO(
//                        googlePlace.getDisplayName().getText(),
//                        googlePlace.getTypes().isEmpty() ? "기타" : googlePlace.getTypes().get(0),
//                        new PlaceResponse.LocationDTO(
//                                googlePlace.getLocation().getLatitude(),
//                                googlePlace.getLocation().getLongitude()
//                        )
//                ))
//                .filter(place -> {
//                    if (category == null || category.trim().isEmpty()){
//                        return true;
//                    }
//                    return category.equals(place.getCategory());
//                })
//                .toList();
//
//        return new  PlaceResponse(places);
//    }





}
