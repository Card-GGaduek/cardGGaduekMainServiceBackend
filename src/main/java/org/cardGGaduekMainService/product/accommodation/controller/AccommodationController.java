package org.cardGGaduekMainService.product.accommodation.controller;

import org.cardGGaduekMainService.exception.CustomException;
import org.cardGGaduekMainService.exception.ErrorCode;
import org.cardGGaduekMainService.product.accommodation.dto.AccommodationPageDTO;
import org.cardGGaduekMainService.product.accommodation.service.AccommodationService;
import org.cardGGaduekMainService.response.ApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccommodationPageDTO>> getAccommodationPage(@PathVariable Long id){
        AccommodationPageDTO pageData = accommodationService.getAccommodationPageDetail(id);
        if(pageData == null){
            throw new CustomException(ErrorCode.ACCOMMODATION_NOT_FOUND);
        }
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.ACCOMMODATION_FETCH_SUCCESS, pageData));
    }
}
