package org.cardGGaduekMainService.totalbenefit.controller;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.response.ApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.cardGGaduekMainService.totalbenefit.dto.TotalBenefitResponseDTO;
import org.cardGGaduekMainService.totalbenefit.service.TotalBenefitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/main")
@RequiredArgsConstructor
public class TotalBenefitController {

    private final TotalBenefitService totalBenefitService;

    @GetMapping("/total-benefit")
    public ResponseEntity<ApiResponse<TotalBenefitResponseDTO>> getTotalBenefit(
            @RequestParam Long memberId,
            @RequestParam String yearMonth) {
        TotalBenefitResponseDTO response = totalBenefitService.getTotalBenefit(memberId, yearMonth);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.TOTAL_BENEFIT_FETCH_SUCCESS, response));
    }
}
