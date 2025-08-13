package org.cardGGaduekMainService.transaction.controller;


import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.auth.dto.LoginMember;
import org.cardGGaduekMainService.response.ApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.cardGGaduekMainService.transaction.dto.CardTransactionsDTO;
import org.cardGGaduekMainService.transaction.dto.TransactionDTO;
import org.cardGGaduekMainService.transaction.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    
    @PostMapping("/api/transaction")
    public ResponseEntity<ApiResponse<Void>> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        transactionService.createTransaction(transactionDTO);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.TRANSACTION_CREATE_SUCCESS));
    }

    @GetMapping("/api/members/cards/transactions")
    public ResponseEntity<ApiResponse<List<CardTransactionsDTO>>> getCardTransactions(
            @AuthenticationPrincipal LoginMember loginMember
            ) {
        Long memberId = loginMember.getId();
        List<CardTransactionsDTO> result = transactionService.getTransactionsGroupedByCard(memberId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.MEMBER_TRANSACTION_FETCH_SUCCESS, result));
    }

}
