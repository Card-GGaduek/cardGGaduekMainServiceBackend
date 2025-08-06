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
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CardPerformanceController {

    private final CardPerformanceService cardPerformanceService;


    @GetMapping("/api/members/cardPerformance")
    public ResponseEntity<ApiResponse<List<CardPerformanceDTO>>> getCardPerformances(
            @AuthenticationPrincipal LoginMember loginMember) {
        Long memberId = loginMember.getId();
        String owner  = loginMember.getName();

        List<CardPerformanceDTO> cardPerformanceDTO = cardPerformanceService.getCardPerformances(memberId)
                .stream()
                .peek(dto -> dto.setOwnerName(owner))
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(
                SuccessCode.CARD_PERFORMANCE_FETCH_SUCCESS, cardPerformanceDTO));
    }





}
