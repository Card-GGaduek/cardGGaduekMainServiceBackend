package org.cardGGaduekMainService.place.service;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.place.dto.GoogleApiRequest;
import org.cardGGaduekMainService.place.dto.GoogleApiResponse;
import org.cardGGaduekMainService.place.dto.PlaceResponse;
import org.cardGGaduekMainService.place.dto.PlaceSearchRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:application-secret.properties")
public class PlaceService {

    private final RestTemplate restTemplate;
    @Value("${google.apikey}")
    private String googleApiKey;
    private final String GOOGLE_MAPS_API_URL = "https://places.googleapis.com/v1/places:searchText";

//    public PlaceResponse findPlaceByName(String name, double latitude, double longitude) {
//
//        Map<String, String> headers = new HashMap<>();
//        headers.put("Content-Type", "application/json");
//        headers.put("X-Goog-FieldMask", "places.displayName,places.formattedAddress,places.location,places.primaryType");
//        headers.put("X-Goog-Api-Key", googleApiKey);
//
//        Map<String, Object> bodyMap = new HashMap<>();
//        bodyMap.put("textQuery", name);
//        bodyMap.put("languageCode", "ko");
//
//        Map<String, Object> location = new HashMap<>();
//        location.put("latitude", latitude);
//        location.put("longitude", longitude);
//
//        Map<String, Object> circle = new HashMap<>();
//        circle.put("center", location);
//        circle.put("radius", 500.0);
//
//        Map<String, Object> locationBias = new HashMap<>();
//        locationBias.put("circle", circle);
//
//        bodyMap.put("locationBias", locationBias);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            String jsonRequestBody = objectMapper.writeValueAsString(bodyMap);
//
//            String response = (String) HttpClientUtil.post(GOOGLE_MAPS_API_URL, headers, jsonRequestBody);
//
//            // TODO: JSON -> PlaceResponse 매핑
//            return objectMapper.readValue(response, PlaceResponse.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new CustomException(ErrorCode.PLACE_FETCH_FAIL);
//        }
//
//
//
//
//
//    }

    public PlaceResponse searchPlace(PlaceSearchRequest request) {
        // google api 요청 본문 생성
        GoogleApiRequest googleApiRequest = new GoogleApiRequest(request.getName(),
                new GoogleApiRequest.LocationRestriction(
                        new GoogleApiRequest.Rectangle(
                                new GoogleApiRequest.LatLng(request.getMinLatitude(),request.getMinLongitude()),
                                new GoogleApiRequest.LatLng(request.getMaxLatitude(),request.getMaxLatitude())

                        )
                )
        );

        // http 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Goog-Api-Key", googleApiKey);
        headers.set("X-Goog-FieldMask", "places.displayName,places.types,places.location");

        // 요청 객체 생성
        HttpEntity<GoogleApiRequest> entity = new HttpEntity<>(googleApiRequest, headers);

        // RestTemplate로 Google API 호출
        ResponseEntity<GoogleApiResponse> responseEntity = restTemplate.exchange(
                GOOGLE_MAPS_API_URL, HttpMethod.POST, entity, GoogleApiResponse.class);
        GoogleApiResponse googleApiResponse = responseEntity.getBody();

        // Google 응답을 프론트엔드용 DTO로 변환
        List<PlaceResponse.PlaceDTO> places = googleApiResponse.getPlace().stream()
                .map(googlePlace -> new PlaceResponse.PlaceDTO(
                        googlePlace.getDisplayName().getText(),
                        googlePlace.getTypes().isEmpty() ? "기타" : googlePlace.getTypes().get(0),
                        new PlaceResponse.LocationDTO(
                                googlePlace.getLocation().getLatitude(),
                                googlePlace.getLocation().getLongitude()
                        )
                ))
                .toList();
        return new  PlaceResponse(places);
    }





}
