package org.cardGGaduekMainService.place.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.common.util.HttpClientUtil;
import org.cardGGaduekMainService.exception.CustomException;
import org.cardGGaduekMainService.exception.ErrorCode;
import org.cardGGaduekMainService.place.domain.PlaceResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:application-secret.properties")
public class PlaceService {

    @Value("${google.apikey}")
    private String googleApiKey;
    private final String GOOGLE_MAPS_API_URL = "https://places.googleapis.com/v1/places:searchText";

    public PlaceResponse findPlaceByName(String name, double latitude, double longitude) {

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Goog-FieldMask", "places.displayName,places.formattedAddress,places.location,places.primaryType");
        headers.put("X-Goog-Api-Key", googleApiKey);

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("textQuery", name);
        bodyMap.put("languageCode", "ko");

        Map<String, Object> location = new HashMap<>();
        location.put("latitude", latitude);
        location.put("longitude", longitude);

        Map<String, Object> circle = new HashMap<>();
        circle.put("center", location);
        circle.put("radius", 500.0);

        Map<String, Object> locationBias = new HashMap<>();
        locationBias.put("circle", circle);

        bodyMap.put("locationBias", locationBias);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonRequestBody = objectMapper.writeValueAsString(bodyMap);

            String response = (String) HttpClientUtil.post(GOOGLE_MAPS_API_URL, headers, jsonRequestBody);

            // TODO: JSON -> PlaceResponse 매핑
            return objectMapper.readValue(response, PlaceResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.PLACE_FETCH_FAIL);
        }





    }





}
