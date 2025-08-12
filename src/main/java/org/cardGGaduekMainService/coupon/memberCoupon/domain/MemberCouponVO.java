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
    private String couponName;
    private String couponCategory;
    private BigDecimal discountValue;
    private String couponStatus;     // 사용 가능, 사용 완료
    private LocalDateTime expiredAt;
    private CouponProductVO couponProduct;
}
