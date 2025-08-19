package org.cardGGaduekMainService.cardPerformance.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.auth.dto.LoginMember;
import org.cardGGaduekMainService.cardPerformance.dto.CardPerformanceDTO;
import org.cardGGaduekMainService.cardPerformance.service.CardPerformanceService;
import org.cardGGaduekMainService.response.CustomApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "카드 실적 관리")
@RestController
@RequiredArgsConstructor
public class CardPerformanceController {

    private final CardPerformanceService cardPerformanceService;


    @ApiOperation(value = "내 카드 실적 조회", notes = "사용자가 보유한 카드들의 실적을 조회하는 API")
    @ApiResponses(
            @ApiResponse(code = 200, message = "실적 조회 성공", response = CardPerformanceDTO.class, responseContainer = "List")
    )
    @GetMapping("/api/members/cardPerformance")
    public ResponseEntity<CustomApiResponse<List<CardPerformanceDTO>>> getCardPerformances(
            @AuthenticationPrincipal LoginMember loginMember) {
        Long memberId = loginMember.getId();
        String owner  = loginMember.getName();

        List<CardPerformanceDTO> cardPerformanceDTO = cardPerformanceService.getCardPerformances(memberId)
                .stream()
                .peek(dto -> dto.setOwnerName(owner))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CustomApiResponse.success(
                SuccessCode.CARD_PERFORMANCE_FETCH_SUCCESS, cardPerformanceDTO));
    }





}
