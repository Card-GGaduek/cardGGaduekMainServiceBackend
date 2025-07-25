package org.cardGGaduekMainService.cardSummary.controller;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.cardSummary.dto.CardSummaryDTO;
import org.cardGGaduekMainService.cardSummary.service.CardSummaryService;
import org.cardGGaduekMainService.response.ApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CardSummaryController {

    private final CardSummaryService cardSummaryService;

    @GetMapping("/api/members/{memberId}/summary")
    public ResponseEntity<ApiResponse<List<CardSummaryDTO>>> getCardSummary(@PathVariable Long memberId) {

        List<CardSummaryDTO> cardSummaries = cardSummaryService.getMonthlyCardSummary(memberId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.CARD_SUMMARY_FETCH_SUCCESS, cardSummaries));
    }


}
