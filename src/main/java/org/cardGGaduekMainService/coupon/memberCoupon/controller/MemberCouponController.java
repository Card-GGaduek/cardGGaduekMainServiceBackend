package org.cardGGaduekMainService.coupon.memberCoupon.controller;// MemberCouponController.java

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.cardGGaduekMainService.auth.dto.LoginMember;
import org.cardGGaduekMainService.coupon.memberCoupon.service.MemberCouponService;
import org.cardGGaduekMainService.member.dto.MemberCouponDTO; // List<MemberCouponVO> 대신 DTO를 임포트
import org.cardGGaduekMainService.response.CustomApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.cardGGaduekMainService.response.SuccessCode;
// ... 다른 import

@Api(tags = "쿠폰 관리")
@RestController
@RequestMapping("/api/member")
@Log4j2
public class MemberCouponController {
    @Autowired
    private MemberCouponService memberCouponService;

    // 이전에 사용하던 메소드는 그대로 두거나, 아래와 같이 수정합니다.
    @ApiOperation(value = "쿠폰 조회", notes = "사용자가 보유한 쿠폰을 조회하는 API")
    @ApiResponses(
            @ApiResponse(code = 200, message = "쿠폰 조회 성공", response = MemberCouponDTO.class)
    )
    @GetMapping("/coupons")
    @ResponseBody
    public ResponseEntity<CustomApiResponse<MemberCouponDTO>> getMyCouponsWithDetails(@AuthenticationPrincipal LoginMember loginMember) { // 메소드 이름과 파라미터는 편의에 맞게 수정 가능

        //Long memberId = 3L; // 실제로는 세션에서 가져오는 로직이 필요합니다.1
        Long memberId = loginMember.getId();
        log.info("조회 요청 회원 ID: " + memberId);

        // 1. 서비스 호출 변경: 새로 만든 메소드를 호출합니다.
        MemberCouponDTO memberWithCoupons = memberCouponService.findMemberWithCoupons(memberId);

        // 2. 반환 객체 변경: List가 아닌 DTO 객체를 그대로 반환합니다.
        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.COUPON_FETCH_SUCCESS, memberWithCoupons));

        }
    }