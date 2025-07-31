package org.cardGGaduekMainService.place.controller;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.place.domain.PlaceResponse;
import org.cardGGaduekMainService.place.service.PlaceService;
import org.cardGGaduekMainService.response.ApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/place")
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping
    public ResponseEntity<ApiResponse<PlaceResponse>> findPlaceByName(@RequestParam String name, @RequestParam double latitude, @RequestParam double longitude) {

        return ResponseEntity.ok(ApiResponse.success(SuccessCode.PLACE_FETCH_SUCCESS, placeService.findPlaceByName(name, latitude, longitude)));

    }
}
