package org.cardGGaduekMainService.coupon.memberCoupon.service;

import lombok.extern.log4j.Log4j2;
import org.cardGGaduekMainService.coupon.memberCoupon.domain.MemberCouponVO;
import org.cardGGaduekMainService.coupon.memberCoupon.mapper.MemberCouponMapper;
import org.cardGGaduekMainService.member.dto.MemberCouponDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Service
public class MemberCouponServiceImpl implements MemberCouponService {

    @Autowired
    private MemberCouponMapper memberCouponMapper;

    @Override
    public List<MemberCouponVO> findCouponsByMember(Long memberId) {
        // 1. Mapper 인터페이스가 getCouponsWithProductInfo(Long memberId) 메소드만 가지고 있으므로,
        //    해당 메소드를 직접 호출합니다.
        //    (참고: 이 방식으로는 statusCodeId를 이용한 필터링은 불가능합니다.)
        return memberCouponMapper.getCouponsWithProductInfo(memberId);
    }

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

    public void issueCouponByMissionReward(Long memberId, Long couponProductId) {
        // 1. 중복 발급 여부 확인
        MemberCouponVO existing = memberCouponMapper.findByMemberIdAndCouponId(memberId, couponProductId);
        if (existing != null) {
            // 이미 발급된 쿠폰
            log.info("⚠️ 이미 발급된 쿠폰입니다 - memberId={}, couponProductId={}", memberId, couponProductId);
            return;
        }

        // 2. 신규 발급
        int result = memberCouponMapper.insertMemberCoupon(memberId, couponProductId);
        if (result > 0) {
            log.info("✅ 쿠폰 발급 완료 - memberId={}, couponProductId={}", memberId, couponProductId);
        } else {
            log.warn("❌ 쿠폰 발급 실패 - memberId={}, couponProductId={}", memberId, couponProductId);
        }
    }

}