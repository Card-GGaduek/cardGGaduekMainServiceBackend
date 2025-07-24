package org.cardGGaduekMainService.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SuccessCode {

    LOGIN_SUCCESS(301, "로그인 성공"),
    MEMBER_CREATE_SUCCESS(101, "회원 가입 성공"),
    VERIFICATION_MAIL_SENT(102, "인증 메일을 발송 성공"),
    VERIFIED_CODE(103, "인증 성공");

    private final int code;
    private final String message;

}
