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
    private Long member_id;
    private Long coupon_product_id;
    private BigDecimal discount_value;
    private String description;
    private String coupon_category;
    private boolean status_code_id;
    private LocalDateTime issued_at;

    private CouponProductVO couponProduct;
}
