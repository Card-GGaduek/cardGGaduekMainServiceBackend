package org.cardGGaduekMainService.card.controller;

import org.cardGGaduekMainService.card.dto.CardFrontDTO;
import org.cardGGaduekMainService.card.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/main/card")
public class CardController {

    @Autowired
    private CardService cardService;

    // 회원이 가진 카드 앞면 정보 리스트 API
    @GetMapping("/front/{memberId}")
    public ResponseEntity<List<CardFrontDTO>> getCardFrontInfo(@PathVariable Long memberId) {
        List<CardFrontDTO> cards = cardService.getCardFrontInfo(memberId);
        return ResponseEntity.ok(cards);
    }
}
