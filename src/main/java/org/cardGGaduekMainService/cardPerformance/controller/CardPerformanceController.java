package org.cardGGaduekMainService.cardPerformance.controller;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.cardPerformance.dto.CardPerformanceDTO;
import org.cardGGaduekMainService.cardPerformance.service.CardPerformanceService;
import org.cardGGaduekMainService.response.ApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.cardGGaduekMainService.transaction.dto.CardTransactionsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CardPerformanceController {

    private final CardPerformanceService cardPerformanceService;


    @GetMapping("/api/members/{memberId}/cardPerformance")
    public ResponseEntity<ApiResponse<List<CardPerformanceDTO>>> getCardPerformances(
            @PathVariable("memberId") Long memberId) {
        List<CardPerformanceDTO> cardPerformances = cardPerformanceService.getCardPerformances(memberId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.CARD_PERFORMANCE_FETCH_SUCCESS, cardPerformances));
    }





}
