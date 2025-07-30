package org.cardGGaduekMainService.cardProduct.controller;

import org.cardGGaduekMainService.cardProduct.domain.CardProductVO;
import org.cardGGaduekMainService.cardProduct.dto.CardProductDTO;
import org.cardGGaduekMainService.cardProduct.service.CardProductService;
import org.cardGGaduekMainService.response.ApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/card-products")
public class CardProductsController {
    private final CardProductService cardProductService;

    @Autowired
    public CardProductsController(CardProductService cardProductService){
        this.cardProductService = cardProductService;
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<CardProductDTO>>> getAllCardProducts() {
        List<CardProductDTO> cardProducts = cardProductService.getAllCardProducts();
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.CARDPRODUCT_FETCH_SUCCESS, cardProducts));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<CardProductVO>> getCardProductDetail(@PathVariable Long productId){
        CardProductVO cardProduct = cardProductService.getCardProductDetail(productId);

        return ResponseEntity.ok(ApiResponse.success(SuccessCode.CARDPRODUCT_FETCH_SUCCESS, cardProduct));
    }
}
