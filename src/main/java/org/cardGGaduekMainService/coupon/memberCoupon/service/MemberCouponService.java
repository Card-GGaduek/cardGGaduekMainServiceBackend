package org.cardGGaduekMainService.coupon.memberCoupon.service;

import org.cardGGaduekMainService.coupon.memberCoupon.domain.MemberCouponVO;
import org.cardGGaduekMainService.member.dto.MemberCouponDTO;

import java.math.BigDecimal;
import java.util.List;


public interface MemberCouponService {
//    /**
//     * 특정 회원에게 쿠폰을 발급합니다.
//     * @param memberId 쿠폰을 발급받을 회원의 ID
//     * @param couponProductId 발급할 쿠폰 상품의 ID
//     * @return 발급된 회원 쿠폰 정보
//     */
//    MemberCouponVO issueCoupon(int memberId, int couponProductId);

    /**
     * 회원이 보유한 쿠폰 목록을 상세 정보와 함께 조회합니다.
     * @param memberId 회원 ID
     * @param status 조회할 쿠폰 상태 (true: 사용완료, false: 사용가능, null: 전체)
     * @return 조회된 회원 쿠폰 목록
     */


    List<MemberCouponVO> findCouponsByMember(Long memberId);

    MemberCouponDTO findMemberWithCoupons(Long memberId);

    MemberCouponVO validateMemberCoupon(Long memberId, Long couponProductid);

    BigDecimal getDiscountAmount(MemberCouponVO memberCouponVO);

//    /**
//     * 회원의 쿠폰을 사용 처리합니다.
//     * @param memberCouponId 사용 처리할 회원 쿠폰의 ID
//     */
//    void useCoupon(int memberCouponId);
//
//    /**
//     * 발급된 쿠폰 한 개의 정보를 조회합니다.
//     * @param memberCouponId 조회할 회원 쿠폰의 ID
//     * @return 조회된 회원 쿠폰 정보
//     */
//    MemberCouponVO findCouponById(int memberCouponId);
}
