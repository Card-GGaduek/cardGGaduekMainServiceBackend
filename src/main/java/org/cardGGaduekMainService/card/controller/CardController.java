package org.cardGGaduekMainService.card.controller;

import org.cardGGaduekMainService.card.dto.CardFrontDTO;
import org.cardGGaduekMainService.card.dto.CardBackDTO;
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

    @GetMapping("/front/{memberId}")
    public ResponseEntity<List<CardFrontDTO>> getCardFrontInfo(@PathVariable Long memberId) {
        return ResponseEntity.ok(cardService.getCardFrontInfo(memberId));
    }

    @GetMapping("/back/{cardId}")
    public ResponseEntity<CardBackDTO> getCardDetail(@PathVariable Long cardId) {
        return ResponseEntity.ok(cardService.getCardDetail(cardId));
    }
}
