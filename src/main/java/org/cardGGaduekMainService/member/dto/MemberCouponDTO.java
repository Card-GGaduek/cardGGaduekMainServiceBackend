package org.cardGGaduekMainService.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cardGGaduekMainService.coupon.memberCoupon.domain.MemberCouponVO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberCouponDTO {
    private Long id;
    private List<MemberCouponVO> memberCoupons;
}
