package org.cardGGaduekMainService.coupon.memberCoupon.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cardGGaduekMainService.coupon.couponProduct.domain.CouponProductVO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberCouponVO {
    private Long id;
    private Long memberId;
    private Long couponProductId;
    private boolean statusCodeId;     // 사용 여부
    private CouponProductVO couponProduct;
}
