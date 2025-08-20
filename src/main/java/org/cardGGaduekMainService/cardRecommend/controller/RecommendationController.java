package org.cardGGaduekMainService.cardRecommend.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.auth.dto.LoginMember;
import org.cardGGaduekMainService.cardRecommend.dto.CardRecommendDTO;
import org.cardGGaduekMainService.cardRecommend.service.RecommendationService;
import org.cardGGaduekMainService.response.CustomApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Api(tags = "카드 추천 관리")
@RestController
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @ApiOperation(value = "카드 추천", notes = "나의 거래내역을 바탕으로 카드를 추천하는 API")
    @ApiResponses(
            @ApiResponse(code = 200, message = "카드 추천 성공", response = CardRecommendDTO.class)
    )
    @GetMapping("/api/recommendations")
    public ResponseEntity<CustomApiResponse<CardRecommendDTO>> getRecommend(
            @AuthenticationPrincipal LoginMember loginMember) {

        Long memberId = loginMember.getId();
        CardRecommendDTO cardRecommendDTO = recommendationService.build(memberId);

        return ResponseEntity.ok(
                CustomApiResponse.success(SuccessCode.CARD_RECOMMEND_FETCH_SUCCESS, cardRecommendDTO)
        );
    }


}
