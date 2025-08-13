package org.cardGGaduekMainService.coupon.memberCoupon.service;

import lombok.extern.log4j.Log4j2;
import org.cardGGaduekMainService.coupon.memberCoupon.domain.MemberCouponVO;
import org.cardGGaduekMainService.coupon.memberCoupon.mapper.MemberCouponMapper;
import org.cardGGaduekMainService.member.dto.MemberCouponDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Log4j2
@Service
public class MemberCouponServiceImpl implements MemberCouponService {

    @Autowired
    private MemberCouponMapper memberCouponMapper;

    public MemberCouponDTO findMemberWithCoupons(Long memberId){
        return memberCouponMapper.getMemberWithCouponsByMemberId(memberId);
    }


    public MemberCouponVO validateMemberCoupon(Long memberId, Long couponProductId){
        MemberCouponVO memberCoupon = memberCouponMapper.findByMemberIdAndCouponId(memberId, couponProductId);
        if (memberCoupon == null) {
            // null이면, 쿠폰을 찾지 못한 것이므로 예외를 던집니다.
            throw new IllegalArgumentException("소유하지 않았거나 유효하지 않은 쿠폰입니다.");
        }

        if (!"사용 가능".equals(memberCoupon.getCouponStatus())) {
            throw new IllegalArgumentException("이미 사용했거나 만료된 쿠폰입니다.");
        }

        if(!"TRAVEL".equals(memberCoupon.getCouponCategory())){
            throw new IllegalArgumentException("카테고리에 맞지 않는 쿠폰입니다.");
        }
        return memberCoupon;
    }

    public BigDecimal getDiscountAmount(MemberCouponVO memberCoupon){
        return memberCoupon.getDiscountValue();
    }
    // MemberCouponService 인터페이스에 정의된 다른 메소드들도 여기에 구현해야 합니다.
    // (예: issueCoupon, useCoupon 등)

}