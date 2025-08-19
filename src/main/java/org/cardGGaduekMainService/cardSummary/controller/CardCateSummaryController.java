package org.cardGGaduekMainService.cardSummary.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.auth.dto.LoginMember;
import org.cardGGaduekMainService.cardSummary.dto.CardCateSummaryDTO;
import org.cardGGaduekMainService.cardSummary.service.CardCateSummaryService;
import org.cardGGaduekMainService.response.CustomApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "카테고리별 카드 지출")
@RestController
@RequiredArgsConstructor
public class CardCateSummaryController {

    private final CardCateSummaryService cardCateSummaryService;

    @ApiOperation(value = "월간 지출이 가장 높은 카테고리 조회", notes = "월간 지출이 가장 높은 카테고리 조회하는 API")
    @ApiResponses(
            @ApiResponse(code = 200, message = "조회 성공", response = CardCateSummaryDTO.class, responseContainer = "List")
    )
    @GetMapping("/api/members/cardCateSummary")
    public ResponseEntity<CustomApiResponse<List<CardCateSummaryDTO>>> getCurrentMonthCategorySummary(
            @AuthenticationPrincipal LoginMember loginMember) {
        Long memberId = loginMember.getId();
        List<CardCateSummaryDTO> cardCateSummaries = cardCateSummaryService.getCurrentMonthCategorySummary(memberId);
        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.CARD_SUMMARY_FETCH_SUCCESS, cardCateSummaries));
    }
}