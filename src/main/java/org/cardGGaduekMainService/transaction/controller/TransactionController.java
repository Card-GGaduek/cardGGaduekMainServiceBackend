package org.cardGGaduekMainService.transaction.controller;

import org.cardGGaduekMainService.response.ApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.cardGGaduekMainService.transaction.dto.CardTransactionsDTO;
import org.cardGGaduekMainService.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {

    private final TransactionService service;

    @Autowired
    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping("/memebers/{memberId}/cards/transactions")
    public ResponseEntity<ApiResponse<List<CardTransactionsDTO>>> getCardTransactions(
            @PathVariable("memberId") Long memberId
    ) {
        List<CardTransactionsDTO> result = service.getTransactionsGroupedByCard(memberId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.MEMBER_TRANSACTION_FETCH_SUCCESS, result));
    }

}
