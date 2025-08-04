package org.cardGGaduekMainService.transaction.controller;


import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.response.ApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.cardGGaduekMainService.transaction.domain.TransactionVO;
import org.cardGGaduekMainService.transaction.dto.CardTransactionsDTO;
import org.cardGGaduekMainService.transaction.dto.TransactionDTO;
import org.cardGGaduekMainService.transaction.service.TransactionService;
import org.springframework.http.ResponseEntity;
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
//    public ResponseEntity<ApiResponse<Void>> insertTransaction(@RequestBody TransactionVO vo) {
//        transactionService.insertTransaction(vo);
//        return ResponseEntity.ok(ApiResponse.success(SuccessCode.TRANSACTION_INSERT_SUCCESS));
//    }

    @GetMapping("/api/members/{memberId}/cards/transactions")
    public ResponseEntity<ApiResponse<List<CardTransactionsDTO>>> getCardTransactions(
            @PathVariable("memberId") Long memberId
    ) {
        List<CardTransactionsDTO> result = transactionService.getTransactionsGroupedByCard(memberId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.MEMBER_TRANSACTION_FETCH_SUCCESS, result));
    }


}
