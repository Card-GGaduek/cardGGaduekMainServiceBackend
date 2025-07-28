package org.cardGGaduekMainService.card.controller;

import org.cardGGaduekMainService.card.dto.CardFrontDTO;
import org.cardGGaduekMainService.card.dto.CardBackDTO;
import org.cardGGaduekMainService.card.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.cardGGaduekMainService.response.ApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import java.util.List;

@RestController
@RequestMapping("/api/main/card")
public class CardController {

    @Autowired
    private CardService cardService;

    @GetMapping("/front/{memberId}")
    public ResponseEntity<ApiResponse<List<CardFrontDTO>>> getCardFrontInfo(@PathVariable Long memberId) {
        List<CardFrontDTO> cards = cardService.getCardFrontInfo(memberId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.CARD_FRONT_FETCH_SUCCESS, cards));
    }

    @GetMapping("/back/{cardId}")
    public ResponseEntity<ApiResponse<CardBackDTO>> getCardDetail(@PathVariable Long cardId) {
        CardBackDTO detail = cardService.getCardDetail(cardId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.CARD_BACK_FETCH_SUCCESS, detail));
    }
}
