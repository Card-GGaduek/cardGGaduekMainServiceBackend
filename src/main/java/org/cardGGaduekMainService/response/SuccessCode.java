package org.cardGGaduekMainService.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SuccessCode {

    LOGIN_SUCCESS(301, "로그인 성공"),
    MEMBER_TRANSACTION_FETCH_SUCCESS(401, "회원 거래내역 조회 성공"),
    MEMBER_CREATE_SUCCESS(101, "회원 가입 성공"),
    COUPON_FETCH_SUCCESS(501, "쿠폰 조회 성공"),
    STORE_SEARCH_SUCCESS (401, "매장 검색 성공"),
    STORE_GET_SUCCESS(402,"매장 조회 성공"),
    NOTIFICATION_FETCH_SUCCESS(201, "알림 조회 성공"),
    VERIFICATION_MAIL_SENT(102, "인증 메일을 발송 성공"),
    VERIFIED_CODE(103, "인증 성공");
  

    private final int code;
    private final String message;

}
