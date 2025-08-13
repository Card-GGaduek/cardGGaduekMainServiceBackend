package org.cardGGaduekMainService.coupon.memberCoupon.service;

import org.cardGGaduekMainService.coupon.memberCoupon.domain.MemberCouponVO;
import org.cardGGaduekMainService.member.dto.MemberCouponDTO;

import java.math.BigDecimal;

public interface MemberCouponService {

    MemberCouponDTO findMemberWithCoupons(Long memberId);

    MemberCouponVO validateMemberCoupon(Long memberId, Long couponProductid);

    BigDecimal getDiscountAmount(MemberCouponVO memberCouponVO);

}
