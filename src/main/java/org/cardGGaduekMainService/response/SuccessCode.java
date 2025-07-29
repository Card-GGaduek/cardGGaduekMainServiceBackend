package org.cardGGaduekMainService.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SuccessCode {

    LOGIN_SUCCESS(301, "로그인 성공"),
    MEMBER_CREATE_SUCCESS(101, "회원 가입 성공"),
    MEMBER_UPDATE_SUCCESS(300, "회원 정보 수정 성공"),
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
    CARD_PERFORMANCE_FETCH_SUCCESS(402, "회원 카드실적 조회 성공"),
    CARD_SUMMARY_FETCH_SUCCESS(403, "회원 카드 요약 조회 성공"),

    COUPON_FETCH_SUCCESS(501, "쿠폰 조회 성공"),
    STORE_SEARCH_SUCCESS (401, "매장 검색 성공"),
    STORE_GET_SUCCESS(402,"매장 조회 성공"),
    STORE_MY_CARD_SUCCESS(403,"내 카드 매장 조회 성공"),

    NOTIFICATION_FETCH_SUCCESS(201, "알림 조회 성공"),
    VERIFICATION_MAIL_SENT(102, "인증 메일을 발송 성공"),
    VERIFIED_CODE(103, "인증 성공"),

    QR_CREATE_SUCCESS(601, "QR 코드 생성 성공"),


    PLACE_FETCH_SUCCESS(503, "장소 조회 성공"),

  
    CARD_FRONT_FETCH_SUCCESS(801, "보유 카드 리스트 조회 성공"),
    CARD_BACK_FETCH_SUCCESS(802, "카드 상세 정보 조회 성공"),


    //BOOKING
    BOOKING_REQUEST_SUCCESS(601, "예약 성공"),
    BOOKING_FETCH_SUCCESS(602, "예약 조회 성공");
  
    private final int code;
    private final String message;

}
