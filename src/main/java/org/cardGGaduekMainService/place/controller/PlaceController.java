package org.cardGGaduekMainService.place.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.place.dto.PlaceResponse;
import org.cardGGaduekMainService.place.dto.PlaceSearchRequest;
import org.cardGGaduekMainService.place.service.PlaceService;
import org.cardGGaduekMainService.response.CustomApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "가맹점")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/place")
@CrossOrigin(origins = "http://localhost:5173")
public class PlaceController {

    private final PlaceService placeService;

    @ApiOperation(value = "가맹점 조회", notes = "검색어를 통해 가맹점을 조회하는 API")
    @ApiResponses(
            @ApiResponse(code = 200, message = "가맹점 조회 성공", response = PlaceResponse.class)
    )
    @PostMapping
    public ResponseEntity<CustomApiResponse<PlaceResponse>> findPlaceByName(@RequestBody PlaceSearchRequest request) {
        String textQuery = request.getTextQuery();
        String languageCode = request.getLanguageCode();
        Double minLat = request.getLocationRestriction().getRectangle().getLow().getLatitude();
        Double maxLat = request.getLocationRestriction().getRectangle().getHigh().getLatitude();
        Double minLon = request.getLocationRestriction().getRectangle().getLow().getLongitude();
        Double maxLon = request.getLocationRestriction().getRectangle().getHigh().getLongitude();
        String category = request.getCategory();

        PlaceResponse response = placeService.findPlaceByName(textQuery, minLat, minLon,maxLat,maxLon);
        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.PLACE_FETCH_SUCCESS, response));

    }

//    @PostMapping ("/search")
//    public ResponseEntity<ApiResponse<PlaceResponse>> searchPlace(@ModelAttribute PlaceSearchRequest request){
//        PlaceResponse response = placeService.searchPlace(request);
//        return ResponseEntity.ok(ApiResponse.success(SuccessCode.PLACE_FETCH_SUCCESS, response));
//    }
}
