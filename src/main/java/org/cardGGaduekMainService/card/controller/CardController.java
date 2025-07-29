package org.cardGGaduekMainService.card.controller;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.response.SuccessCode;
import org.cardGGaduekMainService.card.dto.CardImageDTO;
import org.cardGGaduekMainService.card.service.CardService;
import org.cardGGaduekMainService.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/card")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @DeleteMapping("/{cardId}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long cardId) {
        cardService.deleteCard(cardId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @PatchMapping("/{cardId}/image")
    public ResponseEntity<ApiResponse<Void>> updateCardImage(@PathVariable Long cardId,
                                                             @RequestBody CardImageDTO request) {
        cardService.updateCardImage(cardId, request.getImageUrl());
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.CARD_IMAGE_UPDATE, null));
    }

}
