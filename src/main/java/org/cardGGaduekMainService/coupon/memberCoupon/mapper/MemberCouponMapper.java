package org.cardGGaduekMainService.coupon.memberCoupon.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cardGGaduekMainService.coupon.memberCoupon.domain.MemberCouponVO;
import org.cardGGaduekMainService.member.domain.MemberVO;
import org.cardGGaduekMainService.member.dto.MemberCouponDTO;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface MemberCouponMapper {

    List<MemberCouponVO> getCouponsWithProductInfo(Long memberId);
    MemberCouponDTO getMemberWithCouponsByMemberId(Long memberId);

    MemberCouponVO findByMemberIdAndCouponId(
            @Param("memberId") Long memberId,
            @Param("couponProductId") Long couponProductId
    );

    int insertMemberCoupon(
            @Param("memberId") Long memberId,
            @Param("couponProductId") Long couponProductId
    );

}
