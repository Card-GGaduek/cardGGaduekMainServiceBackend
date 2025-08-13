package org.cardGGaduekMainService.payment.controller;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.auth.dto.LoginMember;
import org.cardGGaduekMainService.payment.dto.PaymentCompleteForm;
import org.cardGGaduekMainService.payment.service.PaymentService;
import org.cardGGaduekMainService.response.ApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {
        "http://localhost:5173", "http://localhost:3000", "http://localhost:8081" // 개발 프론트 도메인
})
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping(
            value = "/payment/complete",
            consumes = "application/x-www-form-urlencoded",
            produces = "application/json"
    )
    public ResponseEntity<ApiResponse<Void>> complete(
            PaymentCompleteForm form,
            @AuthenticationPrincipal LoginMember loginMember
    ) {
        Long loginMemberId = (loginMember != null) ? loginMember.getId() : null;
        paymentService.confirmAndRecord(form, loginMemberId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.TRANSACTION_CREATE_SUCCESS));
    }
}
