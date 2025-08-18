package org.cardGGaduekMainService.cardProduct.controller;

import io.swagger.annotations.*;
import org.cardGGaduekMainService.cardProduct.dto.CardProductDTO;
import org.cardGGaduekMainService.cardProduct.dto.CardProductDetailDTO;
import org.cardGGaduekMainService.cardProduct.service.CardProductService;
import org.cardGGaduekMainService.response.CustomApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "카드 상품 조회")
@RestController
@RequestMapping("/api/card-products")
public class CardProductsController {
    private final CardProductService cardProductService;

    @Autowired
    public CardProductsController(CardProductService cardProductService){
        this.cardProductService = cardProductService;
    }

    @ApiOperation(value = "카드 상품 전체 조회", notes = "모든 카드 상품을 조회하는 API")
    @ApiResponses(
            @ApiResponse(code = 200, message = "카드 상품 조회 성공", response = CardProductDTO.class, responseContainer = "List")
    )
    @GetMapping
    public ResponseEntity<CustomApiResponse<List<CardProductDTO>>> getAllCardProducts() {
        List<CardProductDTO> cardProducts = cardProductService.getAllCardProducts();
        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.CARDPRODUCT_FETCH_SUCCESS, cardProducts));
    }

    @ApiOperation(value = "카드 상품 상세 조회", notes = "특정 카드 상품 상세 조회 API")
    @ApiResponses(
            @ApiResponse(code = 200, message = "카드 상품 상세 조회 성공", response = CardProductDetailDTO.class)
    )
    @GetMapping("/{productId}")
    public ResponseEntity<CustomApiResponse<CardProductDetailDTO>> getCardProductDetail(@ApiParam(value = "카드 상품 ID") @PathVariable Long productId){
        CardProductDetailDTO cardProduct = cardProductService.getCardProductDetail(productId);

        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.CARDPRODUCT_FETCH_SUCCESS, cardProduct));
    }
}