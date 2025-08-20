package org.cardGGaduekMainService.auth.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.auth.dto.TokenResponse;
import org.cardGGaduekMainService.auth.dto.MemberJoinRequest;
import org.cardGGaduekMainService.auth.dto.NaverAuthRequest;
import org.cardGGaduekMainService.auth.service.AuthService;
import org.cardGGaduekMainService.auth.service.NaverLoginService;
import org.cardGGaduekMainService.common.mail.service.EmailService;
import org.cardGGaduekMainService.auth.dto.MemberLoginRequest;
import org.cardGGaduekMainService.member.service.MemberService;
import org.cardGGaduekMainService.response.CustomApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Api(tags = "로그인 및 권한 관리")
//@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final EmailService emailService;
    private final MemberService memberService;
    private final NaverLoginService naverLoginService;

    @ApiOperation(value = "일반 로그인", notes = "아이디, 패스퉈드를 통해 로그인 요청을 하는 API")
    @ApiResponses(
            @ApiResponse(code = 200, message = "로그인 성공", response = TokenResponse.class)
    )
    @PostMapping("/login")
    public ResponseEntity<CustomApiResponse<TokenResponse>> login(@ApiParam(value = "아이디, 패스워드", required = true) @RequestBody MemberLoginRequest memberLoginRequest) {
        String email = memberLoginRequest.getEmail();
        String pwd = memberLoginRequest.getPassword();

        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.LOGIN_SUCCESS, authService.memberLogin(email, pwd)));
    }


    @ApiOperation(value = "회원가입", notes = "일반 회원가입 API")
    @ApiResponses(
            @ApiResponse(code = 200, message = "회원가입 성공")
    )
    @PostMapping("/join")
    public ResponseEntity<CustomApiResponse<Void>> registerMember(@ApiParam(value = "이메일, 패스워드, 이름, 전화번호", required = true) @RequestBody MemberJoinRequest memberJoinRequest) {
        memberService.addMember(memberJoinRequest);
        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.MEMBER_CREATE_SUCCESS));
    }


    @ApiOperation(value = "이메일 인증코드 발송", notes = "회원가입 과정에서 이메일 인증을 위한 이메일 발송 API")
    @ApiResponses(
            @ApiResponse(code = 200, message = "이메일 발송 성공")
    )
    @GetMapping("/join/emailConfirm")
    public ResponseEntity<CustomApiResponse<Void>> sendVerificationCode(@ApiParam(value = "이메일", required = true) @RequestParam String email) throws MessagingException {
        emailService.sendVerificationCode(email);
        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.VERIFICATION_MAIL_SENT));
    }

    @ApiOperation(value = "이메일 인증", notes = "이메일로 발송된 인증코드를 인증하는 API")
    @ApiResponses(
            @ApiResponse(code = 200, message = "이메일 인증 성공")
    )
    @PostMapping("/join/verifyCode")
    public ResponseEntity<CustomApiResponse<Void>> verifyCode(@ApiParam(value = "이메일, 인증코드") @RequestBody Map<String, String> body) {
        String email = body.get("email");
        String code = body.get("code");

//        System.out.println("hhhh");

        emailService.verifyEmailCode(email, code);

        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.VERIFIED_CODE));
    }

    @ApiOperation(value = "네이버 로그인", notes = "네이버 소셜 로그인 API")
    @ApiResponses(
            @ApiResponse(code = 200, message = "네이버 소셜 로그인 성공")
    )
    @PostMapping("/naverLogin")
    public ResponseEntity<CustomApiResponse<TokenResponse>> naverLogin(@RequestBody NaverAuthRequest request) {
        String code = request.getCode();
        String state = request.getState();

        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.LOGIN_SUCCESS, naverLoginService.naverLogin(code, state)));



    }

}
