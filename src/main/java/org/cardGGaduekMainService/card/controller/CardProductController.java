package org.cardGGaduekMainService.card.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.cardGGaduekMainService.cardProduct.domain.CardProductVO;
import org.cardGGaduekMainService.cardProduct.service.CardProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "카드 상품 관리")
@RestController
@RequestMapping("/api/main/card")
public class CardProductController {

    @Autowired
    private CardProductService cardProductService;

    @ApiOperation(value = "카드 상품 조회", notes = "혜택이 좋은 카드를 조회하는 API")
    @ApiResponses(
            @ApiResponse(code = 200, message = "카드 상품 조회 성공", response = CardProductVO.class, responseContainer = "List" )
    )
    @GetMapping("/topbenefit")
    public List<CardProductVO> getTop5CardProductsByBenefitCount() {
        return cardProductService.getTop5CardProductsByBenefitCount();
    }
}
