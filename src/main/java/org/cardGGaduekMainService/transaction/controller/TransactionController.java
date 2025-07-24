package org.cardGGaduekMainService.transaction.controller;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.response.ApiResponse;
import org.cardGGaduekMainService.transaction.domain.TransactionVO;
import org.cardGGaduekMainService.transaction.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> insertTransaction(@RequestBody TransactionVO vo) {
        transactionService.insertTransaction(vo);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.TRANSACTION_INSERT_SUCCESS));
    }
}
