package org.cardGGaduekMainService.cardSummary.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.auth.dto.LoginMember;
import org.cardGGaduekMainService.cardSummary.dto.CardSummaryDTO;
import org.cardGGaduekMainService.cardSummary.service.CardSummaryService;
import org.cardGGaduekMainService.response.CustomApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "카드 지출")
@RestController
@RequiredArgsConstructor
public class CardSummaryController {

    private final CardSummaryService cardSummaryService;


    @ApiOperation(value = "회원 지출 내역", notes = "회원의 월별 지출내역 API")
    @ApiResponses(
            @ApiResponse(code = 200, message = "지출 내역 조회 성공", response = CardSummaryDTO.class, responseContainer = "List")
    )
    @GetMapping("/api/members/summary")
    public ResponseEntity<CustomApiResponse<List<CardSummaryDTO>>> getCardSummary(@AuthenticationPrincipal LoginMember loginMember) {
        Long memberId = loginMember.getId();

        List<CardSummaryDTO> cardSummaries = cardSummaryService.getMonthlyCardSummary(memberId);
        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.CARD_SUMMARY_FETCH_SUCCESS, cardSummaries));
    }


}
