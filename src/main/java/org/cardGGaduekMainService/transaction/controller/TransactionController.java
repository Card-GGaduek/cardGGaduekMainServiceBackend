package org.cardGGaduekMainService.transaction.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.auth.dto.LoginMember;
import org.cardGGaduekMainService.response.CustomApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.cardGGaduekMainService.transaction.dto.CardTransactionsDTO;
import org.cardGGaduekMainService.transaction.dto.TransactionDTO;
import org.cardGGaduekMainService.transaction.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "거래 내역")
@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @ApiOperation(value = "거래 생성", notes = "거래 생성 API")
    @ApiResponses(
            @ApiResponse(code = 200, message = "")
    )
    @PostMapping("/api/transaction")
    public ResponseEntity<CustomApiResponse<Void>> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        transactionService.createTransaction(transactionDTO);
        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.TRANSACTION_CREATE_SUCCESS));
    }

    @ApiOperation(value = "카드 거래내역 조회", notes = "카드 거래 내역 조회 API")
    @ApiResponses(
            @ApiResponse(code = 200, message = "카드 거래내역 조회 성공", response = CardTransactionsDTO.class, responseContainer = "List")
    )
    @GetMapping("/api/members/cards/transactions")
    public ResponseEntity<CustomApiResponse<List<CardTransactionsDTO>>> getCardTransactions(
            @AuthenticationPrincipal LoginMember loginMember
            ) {
        Long memberId = loginMember.getId();
        List<CardTransactionsDTO> result = transactionService.getTransactionsGroupedByCard(memberId);
        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.MEMBER_TRANSACTION_FETCH_SUCCESS, result));
    }

}
