package org.cardGGaduekMainService.card.controller;

import com.google.protobuf.Api;
import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.card.domain.CardVO;
import org.cardGGaduekMainService.card.dto.CardBackDTO;
import org.cardGGaduekMainService.card.dto.CardDTO;
import org.cardGGaduekMainService.card.dto.CardFrontDTO;
import org.cardGGaduekMainService.response.SuccessCode;
import org.cardGGaduekMainService.card.dto.CardImageDTO;
import org.cardGGaduekMainService.card.service.CardService;
import org.cardGGaduekMainService.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponse<List<CardDTO>>> getCardsByMember(@PathVariable Long memberId){
        List<CardDTO> cards = cardService.findCardByMember(memberId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.CARDPRODUCT_FETCH_SUCCESS, cards));
    }
//    @GetMapping("/{memberId}")
//    public ResponseEntity<List<CardVO>> getCardsByMember(@PathVariable Long memberId) {
//        List<CardVO> cards = cardService.findCardsByMember(memberId);
//        return ResponseEntity.ok(ApiResponse.success(SuccessCode.CARD_SUMMARY_FETCH_SUCCESS, cards));
//    }
}
