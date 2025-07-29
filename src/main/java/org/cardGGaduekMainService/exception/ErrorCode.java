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
    INVALID_PASSWORD(4003, "올바르지 않은 비밀번호", HttpStatus.UNAUTHORIZED),
    TRANSACTION_NOT_FOUND(4004, "거래가 존재하지 않습니다", HttpStatus.NOT_FOUND),
    STORE_NOT_FOUND(2001, "매장을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NO_STORES_FOR_MEMBER(2002, "회원의 카드 혜택으로 이용 가능한 매장이 없습니다.", HttpStatus.NOT_FOUND),
    NOTIFICATION_NOT_FOUND(2001, "알림을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    INVALID_MAIL(4003, "유효하지 않은 이메일", HttpStatus.NOT_FOUND),
    INVALID_CODE(4004, "인증 번호가 일치하지 않습니다.", HttpStatus.CONFLICT),
    QR_GENERATION_FAILED(5001, "QR 코드 생성에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    CARD_NOT_FOUND(3001, "카드 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);




    private final int code;
    private final String message;
    private final HttpStatus status;

}
