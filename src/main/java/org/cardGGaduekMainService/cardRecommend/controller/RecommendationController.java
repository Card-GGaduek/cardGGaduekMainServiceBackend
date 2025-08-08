package org.cardGGaduekMainService.cardRecommend.controller;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.auth.dto.LoginMember;
import org.cardGGaduekMainService.cardRecommend.dto.CardRecommendDTO;
import org.cardGGaduekMainService.cardRecommend.service.RecommendationService;
import org.cardGGaduekMainService.response.ApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping("/api/recommendations")
    public ResponseEntity<ApiResponse<CardRecommendDTO>> getRecommend(
            @AuthenticationPrincipal LoginMember loginMember) {

        Long memberId = loginMember.getId();
        CardRecommendDTO cardRecommendDTO = recommendationService.build(memberId);

        return ResponseEntity.ok(
                ApiResponse.success(SuccessCode.CARD_RECOMMEND_FETCH_SUCCESS, cardRecommendDTO)
        );
    }


}
