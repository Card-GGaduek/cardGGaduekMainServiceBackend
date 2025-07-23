package org.cardGGaduekMainService.auth.controller;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.auth.TokenResponse;
import org.cardGGaduekMainService.auth.service.AuthService;
import org.cardGGaduekMainService.member.dto.MemberLoginDTO;
import org.cardGGaduekMainService.response.ApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/api/member/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(@RequestBody MemberLoginDTO memberLoginDTO) {
        String email = memberLoginDTO.getEmail();
        String pwd = memberLoginDTO.getPassword();

        return ResponseEntity.ok(ApiResponse.success(SuccessCode.LOGIN_SUCCESS, authService.memberLogin(email, pwd)));
    }

}
