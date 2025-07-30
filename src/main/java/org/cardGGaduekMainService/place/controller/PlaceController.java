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
public class PlaceController {

    private final PlaceService placeService;

//    @GetMapping
//    public ResponseEntity<ApiResponse<PlaceResponse>> findPlaceByName(@ModelAttribute PlaceSearchRequest request) {
//
//        PlaceResponse response = placeService.
//        return ResponseEntity.ok(ApiResponse.success(SuccessCode.PLACE_FETCH_SUCCESS, placeService.findPlaceByName(name, latitude, longitude)));
//
//    }
    @GetMapping
    public ResponseEntity<ApiResponse<PlaceResponse>> searchPlace(@ModelAttribute PlaceSearchRequest request){
        PlaceResponse response = placeService.searchPlace(request);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.PLACE_FETCH_SUCCESS, response));
    }
}
