package org.cardGGaduekMainService.main.controller;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.main.dto.CardBackDTO;
import org.cardGGaduekMainService.main.dto.CardFrontDTO;
import org.cardGGaduekMainService.main.service.MainCardService;
import org.cardGGaduekMainService.response.ApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/main/card")
@RequiredArgsConstructor
public class MainCardController {
    private final MainCardService service;

    @GetMapping("/{cardId}/front")
    public ResponseEntity<ApiResponse<CardFrontDTO>> getCardFront(@PathVariable Long cardId) {
        CardFrontDTO dto = service.getCardFront(cardId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.CARD_FRONT_FETCH_SUCCESS, dto));
    }

    @GetMapping("/{cardId}/back")
    public ResponseEntity<ApiResponse<CardBackDTO>> getCardBack(@PathVariable Long cardId) {
        CardBackDTO dto = service.getCardBack(cardId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.CARD_BACK_FETCH_SUCCESS, dto));
    }
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<CardFrontDTO>>> getCardList(@RequestParam Long memberId) {
        List<CardFrontDTO> list = service.getCardList(memberId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.CARD_FRONT_FETCH_SUCCESS, list));
    }

}
