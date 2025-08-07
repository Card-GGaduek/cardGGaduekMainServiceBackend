package org.cardGGaduekMainService.place.controller;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.place.dto.PlaceResponse;
import org.cardGGaduekMainService.place.dto.PlaceSearchRequest;
import org.cardGGaduekMainService.place.service.PlaceService;
import org.cardGGaduekMainService.response.ApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/place")
@CrossOrigin(origins = "http://localhost:5173")
public class PlaceController {

    private final PlaceService placeService;

    @PostMapping
    public ResponseEntity<ApiResponse<PlaceResponse>> findPlaceByName(@RequestBody PlaceSearchRequest request) {
        String textQuery = request.getTextQuery();
        String languageCode = request.getLanguageCode();
        Double minLat = request.getLocationRestriction().getRectangle().getLow().getLatitude();
        Double maxLat = request.getLocationRestriction().getRectangle().getHigh().getLatitude();
        Double minLon = request.getLocationRestriction().getRectangle().getLow().getLongitude();
        Double maxLon = request.getLocationRestriction().getRectangle().getHigh().getLongitude();
        String category = request.getCategory();

        PlaceResponse response = placeService.findPlaceByName(textQuery, minLat, minLon,maxLat,maxLon);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.PLACE_FETCH_SUCCESS, response));

    }

//    @PostMapping ("/search")
//    public ResponseEntity<ApiResponse<PlaceResponse>> searchPlace(@ModelAttribute PlaceSearchRequest request){
//        PlaceResponse response = placeService.searchPlace(request);
//        return ResponseEntity.ok(ApiResponse.success(SuccessCode.PLACE_FETCH_SUCCESS, response));
//    }
}
