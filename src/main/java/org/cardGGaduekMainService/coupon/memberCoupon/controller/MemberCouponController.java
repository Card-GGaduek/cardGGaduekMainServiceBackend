package org.cardGGaduekMainService.coupon.memberCoupon.controller;// MemberCouponController.java

import lombok.extern.log4j.Log4j2;
import org.cardGGaduekMainService.coupon.memberCoupon.service.MemberCouponService;
import org.cardGGaduekMainService.member.dto.MemberCouponDTO; // List<MemberCouponVO> 대신 DTO를 임포트
import org.cardGGaduekMainService.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.cardGGaduekMainService.response.SuccessCode;
import org.cardGGaduekMainService.response.ApiResponse;
import org.cardGGaduekMainService.exception.ErrorCode;

import javax.servlet.http.HttpSession;
import java.util.List;
// ... 다른 import

@Controller
@RequestMapping("/api/member")
@Log4j2
public class MemberCouponController {
    @Autowired
    private MemberCouponService memberCouponService;

    // 이전에 사용하던 메소드는 그대로 두거나, 아래와 같이 수정합니다.
    @GetMapping("/coupons")
    @ResponseBody
    public ResponseEntity<ApiResponse<MemberCouponDTO>> getMyCouponsWithDetails(@RequestParam Long memberId, HttpSession session) { // 메소드 이름과 파라미터는 편의에 맞게 수정 가능

        //Long memberId = 3L; // 실제로는 세션에서 가져오는 로직이 필요합니다.1
        log.info("조회 요청 회원 ID: " + memberId);

        // 1. 서비스 호출 변경: 새로 만든 메소드를 호출합니다.
        MemberCouponDTO memberWithCoupons = memberCouponService.findMemberWithCoupons(memberId);

        // 2. 반환 객체 변경: List가 아닌 DTO 객체를 그대로 반환합니다.
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.COUPON_FETCH_SUCCESS, memberWithCoupons));

        }
    }