package org.cardGGaduekMainService.totalbenefit.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.auth.dto.LoginMember;
import org.cardGGaduekMainService.response.CustomApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.cardGGaduekMainService.totalbenefit.dto.TotalBenefitResponseDTO;
import org.cardGGaduekMainService.totalbenefit.service.TotalBenefitService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "혜택")
@RestController
@RequestMapping("/api/main")
@RequiredArgsConstructor
public class TotalBenefitController {

    private final TotalBenefitService totalBenefitService;

    @ApiOperation(value = "", notes = "")
    @ApiResponses(
            @ApiResponse(code = 200, message = "")
    )
    @GetMapping("/total-benefit")
    public ResponseEntity<CustomApiResponse<TotalBenefitResponseDTO>> getTotalBenefit(
            @AuthenticationPrincipal LoginMember loginMember,  // 변경
            @RequestParam String yearMonth) {

        Long memberId = loginMember.getId();  // 추가
        TotalBenefitResponseDTO response = totalBenefitService.getTotalBenefit(memberId, yearMonth);
        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.TOTAL_BENEFIT_FETCH_SUCCESS, response));
    }
}