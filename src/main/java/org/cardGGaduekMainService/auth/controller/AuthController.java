package org.cardGGaduekMainService.auth.controller;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.auth.dto.TokenResponse;
import org.cardGGaduekMainService.auth.dto.MemberJoinRequest;
import org.cardGGaduekMainService.auth.dto.NaverAuthRequest;
import org.cardGGaduekMainService.auth.service.AuthService;
import org.cardGGaduekMainService.auth.service.NaverLoginService;
import org.cardGGaduekMainService.common.mail.service.EmailService;
import org.cardGGaduekMainService.auth.dto.MemberLoginRequest;
import org.cardGGaduekMainService.member.service.MemberService;
import org.cardGGaduekMainService.response.ApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final EmailService emailService;
    private final MemberService memberService;
    private final NaverLoginService naverLoginService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(@RequestBody MemberLoginRequest memberLoginRequest) {
        String email = memberLoginRequest.getEmail();
        String pwd = memberLoginRequest.getPassword();

        return ResponseEntity.ok(ApiResponse.success(SuccessCode.LOGIN_SUCCESS, authService.memberLogin(email, pwd)));
    }

    @PostMapping("/join")
    public ResponseEntity<ApiResponse<Void>> registerMember(@RequestBody MemberJoinRequest memberJoinRequest) {
        memberService.addMember(memberJoinRequest);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.MEMBER_CREATE_SUCCESS));
    }


    @GetMapping("/join/emailConfirm")
    public ResponseEntity<ApiResponse<Void>> sendVerificationCode(@RequestParam String email) throws MessagingException {
        emailService.sendVerificationCode(email);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.VERIFICATION_MAIL_SENT));
    }

    @PostMapping("/join/verifyCode")
    public ResponseEntity<ApiResponse<Void>> verifyCode(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String code = body.get("code");

//        System.out.println("hhhh");

        emailService.verifyEmailCode(email, code);

        return ResponseEntity.ok(ApiResponse.success(SuccessCode.VERIFIED_CODE));
    }

    @PostMapping("/naverLogin")
    public ResponseEntity<ApiResponse<TokenResponse>> naverLogin(@RequestBody NaverAuthRequest request) {
        String code = request.getCode();
        String state = request.getState();

        return ResponseEntity.ok(ApiResponse.success(SuccessCode.LOGIN_SUCCESS, naverLoginService.naverLogin(code, state)));



    }

}
