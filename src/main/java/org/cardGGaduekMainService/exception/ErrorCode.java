package org.cardGGaduekMainService.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    MEMBER_NOT_FOUND(1001, "회원 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    MEMBER_EMAIL_DUPLICATE(1002, "이미 사용중인 이메일입니다.", HttpStatus.CONFLICT),
    INVALID_TOKEN(4001, "유효하지 않은 토큰", HttpStatus.UNAUTHORIZED),
    INVALID_PASSWORD(4003, "올바르지 않은 비밀번호", HttpStatus.UNAUTHORIZED);



    private final int code;
    private final String message;
    private final HttpStatus status;

}
