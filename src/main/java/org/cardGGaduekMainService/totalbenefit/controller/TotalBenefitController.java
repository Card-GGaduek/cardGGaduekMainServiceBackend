package org.cardGGaduekMainService.totalbenefit.controller;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.auth.dto.LoginMember;
import org.cardGGaduekMainService.response.ApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.cardGGaduekMainService.totalbenefit.dto.TotalBenefitResponseDTO;
import org.cardGGaduekMainService.totalbenefit.service.TotalBenefitService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/main")
@RequiredArgsConstructor
public class TotalBenefitController {

    private final TotalBenefitService totalBenefitService;

    @GetMapping("/total-benefit")
    public ResponseEntity<ApiResponse<TotalBenefitResponseDTO>> getTotalBenefit(
            @AuthenticationPrincipal LoginMember loginMember,  // 변경
            @RequestParam String yearMonth) {

        Long memberId = loginMember.getId();  // 추가
        TotalBenefitResponseDTO response = totalBenefitService.getTotalBenefit(memberId, yearMonth);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.TOTAL_BENEFIT_FETCH_SUCCESS, response));
    }
}