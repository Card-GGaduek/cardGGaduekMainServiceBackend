package org.cardGGaduekMainService.payment.controller;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.auth.dto.LoginMember;
import org.cardGGaduekMainService.payment.dto.PaymentCompleteForm;
import org.cardGGaduekMainService.payment.service.PaymentService;
import org.cardGGaduekMainService.response.ApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
@CrossOrigin(origins = {"http://localhost:5173","http://localhost:3000","http://localhost:8081"})
public class PaymentController {

    private final PaymentService paymentService;

    // 폼 바인딩 시 공백/빈 문자열을 null 로 처리 (숫자형 변환 실패 방지)
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // 빈 문자열을 null 로
        binder.registerCustomEditor(Long.class, new org.springframework.beans.propertyeditors.CustomNumberEditor(Long.class, true));
        binder.registerCustomEditor(BigDecimal.class, new org.springframework.beans.propertyeditors.CustomNumberEditor(BigDecimal.class, true));
    }

    /**
     * 1) x-www-form-urlencoded 로 들어오는 요청 처리
     *   - 바인딩 에러를 잡아 400 원인 파악 가능
     */
    @PostMapping(
            value = "/complete",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> completeForm(
            PaymentCompleteForm form,
            BindingResult bindingResult,
            @AuthenticationPrincipal LoginMember loginMember
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(e -> e.getField() + "=" + String.valueOf(e.getRejectedValue()) + " (" + e.getDefaultMessage() + ")")
                    .toList();
            return ResponseEntity.badRequest().body(Map.of("success", false, "errors", errors));
        }

        Long loginMemberId = (loginMember != null) ? loginMember.getId() : null;
        paymentService.confirmAndRecord(form, loginMemberId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.TRANSACTION_CREATE_SUCCESS));
    }

    /**
     * 2) application/json 으로 들어오는 요청 처리
     *   - 프론트에서 JSON 전송 시 타입 변환 이슈가 훨씬 줄어듭니다.
     */
    @PostMapping(
            value = "/complete",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiResponse<Void>> completeJson(
            @RequestBody PaymentCompleteForm form,
            @AuthenticationPrincipal LoginMember loginMember
    ) {
        Long loginMemberId = (loginMember != null) ? loginMember.getId() : null;
        paymentService.confirmAndRecord(form, loginMemberId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.TRANSACTION_CREATE_SUCCESS));
    }
}
