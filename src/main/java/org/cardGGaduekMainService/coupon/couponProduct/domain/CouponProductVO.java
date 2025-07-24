package org.cardGGaduekMainService.coupon.couponProduct.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CouponProductVO {
    private Long id;
    private String coupon_name;
    private int coupon_quantity;
    private String description;
    private LocalDateTime issued_at;
    private LocalDateTime expired_at;
    private BigDecimal discount_value;
}