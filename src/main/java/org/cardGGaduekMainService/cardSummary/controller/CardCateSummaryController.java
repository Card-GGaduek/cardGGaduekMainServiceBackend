package org.cardGGaduekMainService.cardSummary.controller;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.auth.dto.LoginMember;
import org.cardGGaduekMainService.cardSummary.dto.CardCateSummaryDTO;
import org.cardGGaduekMainService.cardSummary.dto.CardSummaryDTO;
import org.cardGGaduekMainService.cardSummary.service.CardCateSummaryService;
import org.cardGGaduekMainService.response.ApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CardCateSummaryController {

    private final CardCateSummaryService cardCateSummaryService;

    @GetMapping("/api/members/cardCateSummary")
    public ResponseEntity<ApiResponse<List<CardCateSummaryDTO>>> getCurrentMonthCategorySummary(
            @AuthenticationPrincipal LoginMember loginMember) {
        Long memberId = loginMember.getId();
        List<CardCateSummaryDTO> cardCateSummaries = cardCateSummaryService.getCurrentMonthCategorySummary(memberId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.CARD_SUMMARY_FETCH_SUCCESS, cardCateSummaries));
    }
}