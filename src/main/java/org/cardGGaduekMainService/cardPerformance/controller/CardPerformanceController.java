package org.cardGGaduekMainService.cardPerformance.controller;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.auth.dto.LoginMember;
import org.cardGGaduekMainService.cardPerformance.dto.CardPerformanceDTO;
import org.cardGGaduekMainService.cardPerformance.service.CardPerformanceService;
import org.cardGGaduekMainService.response.ApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CardPerformanceController {

    private final CardPerformanceService cardPerformanceService;


    @GetMapping("/api/members/cardPerformance")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<List<CardPerformanceDTO>>> getCardPerformances(
            @AuthenticationPrincipal LoginMember loginMember) {
        System.out.println(loginMember);
        Long memberId = loginMember.getId();
        List<CardPerformanceDTO> cardPerformances = cardPerformanceService.getCardPerformances(memberId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.CARD_PERFORMANCE_FETCH_SUCCESS, cardPerformances));
    }





}
