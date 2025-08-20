package org.cardGGaduekMainService.product.accommodation.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.cardGGaduekMainService.exception.CustomException;
import org.cardGGaduekMainService.exception.ErrorCode;
import org.cardGGaduekMainService.product.accommodation.dto.AccommodationPageDTO;
import org.cardGGaduekMainService.product.accommodation.service.AccommodationService;
import org.cardGGaduekMainService.response.CustomApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accommodations")
public class AccommodationController {
    private final AccommodationService accommodationService;

    public AccommodationController(AccommodationService accommodationService){
        this.accommodationService = accommodationService;
    }

    @ApiOperation(value = "예약 정보 조회", notes = "예약 정보를 조회하는 API")
    @ApiResponses(
            @ApiResponse(code = 200, message = "예약 정보 조회 성공")
    )
    @GetMapping("/{id}")
    public ResponseEntity<CustomApiResponse<AccommodationPageDTO>> getAccommodationPage(@ApiParam(value = "예약정보 ID") @PathVariable Long id){
        AccommodationPageDTO pageData = accommodationService.getAccommodationPageDetail(id);
        if(pageData == null){
            throw new CustomException(ErrorCode.ACCOMMODATION_NOT_FOUND);
        }
        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.ACCOMMODATION_FETCH_SUCCESS, pageData));
    }
}
