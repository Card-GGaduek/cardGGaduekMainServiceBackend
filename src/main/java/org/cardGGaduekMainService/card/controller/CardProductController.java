package org.cardGGaduekMainService.card.controller;

import org.cardGGaduekMainService.cardProduct.domain.CardProductVO;
import org.cardGGaduekMainService.cardProduct.service.CardProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/main/card")
public class CardProductController {

    @Autowired
    private CardProductService cardProductService;

    @GetMapping("/topbenefit")
    public List<CardProductVO> getTop5CardProductsByBenefitCount() {
        return cardProductService.getTop5CardProductsByBenefitCount();
    }
}
