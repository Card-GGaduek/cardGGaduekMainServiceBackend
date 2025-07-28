package org.cardGGaduekMainService.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SuccessCode {

    LOGIN_SUCCESS(301, "로그인 성공"),
    MEMBER_CREATE_SUCCESS(101, "회원 가입 성공"),

    // LAB
    MISSION_PROGRESS_FETCH_SUCCESS(201, "미션 진행 현황 조회 성공"),
    FORTUNE_FETCH_SUCCESS(202, "오늘의 소비 운세 조회 성공"),
    ANALYSIS_FETCH_SUCCESS(203, "소비 성향 분석 결과 조회 성공"),

    // LABIMAGE
    IMAGE_LUCKY_ITEM_SENT(701, "행운의 아이템 이미지 전송 성공"),
    IMAGE_ANALYSIS_SENT(702, "소비 성향 분석 이미지 전송 성공"),

    // CARDIMAGE
    CARD_IMAGE_UPDATE(703, "카드 이미지 변경 성공"),

    //TRANSACTION
    TRANSACTION_CREATE_SUCCESS(201, "거래 내역 등록 성공"),
    MEMBER_TRANSACTION_FETCH_SUCCESS(401, "회원 거래내역 조회 성공"),

    COUPON_FETCH_SUCCESS(501, "쿠폰 조회 성공"),
    STORE_SEARCH_SUCCESS (401, "매장 검색 성공"),
    STORE_GET_SUCCESS(402,"매장 조회 성공"),
    NOTIFICATION_FETCH_SUCCESS(201, "알림 조회 성공"),
    VERIFICATION_MAIL_SENT(102, "인증 메일을 발송 성공"),
    VERIFIED_CODE(103, "인증 성공");

  

    private final int code;
    private final String message;

}
