package org.cardGGaduekMainService.common.mail.controller;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.common.mail.service.EmailService;
import org.cardGGaduekMainService.member.dto.MemberJoinDTO;
import org.cardGGaduekMainService.response.ApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EmailController {

    private final EmailService emailService;

    @GetMapping("/join/emailConfirm")
    public ResponseEntity<ApiResponse<Void>> sendVerificationCode(@RequestParam String email) throws MessagingException {
        emailService.sendVerificationCode(email);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.VERIFICATION_MAIL_SENT));
    }

    @PostMapping("/join/verifyCode")
    public ResponseEntity<ApiResponse<Void>> verifyCode(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String code = body.get("code");

        emailService.verifyEmailCode(email, code);

        return ResponseEntity.ok(ApiResponse.success(SuccessCode.VERIFIED_CODE));

    }




}
