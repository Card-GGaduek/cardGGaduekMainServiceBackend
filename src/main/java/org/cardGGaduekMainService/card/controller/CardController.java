package org.cardGGaduekMainService.card.controller;

import org.cardGGaduekMainService.card.dto.CardResponseDTO;
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

    @GetMapping("/{memberId}")
    public ResponseEntity<List<CardResponseDTO>> getUserCards(@PathVariable Long memberId) {
        List<CardResponseDTO> cards = cardService.getCardsByMemberId(memberId);
        return ResponseEntity.ok(cards);
    }
}
