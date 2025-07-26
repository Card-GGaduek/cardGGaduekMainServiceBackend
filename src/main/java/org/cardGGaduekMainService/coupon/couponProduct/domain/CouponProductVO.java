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
    private Long id;                    // 쿠폰 ID
    private String couponName;          // 쿠폰명
    private LocalDateTime expiredAt;    // 쿠폰 만료일
    private Long discountValue;         // 쿠폰 할인금액
    private String couponCategory;      // 쿠폰 카테고리 (FOOD, SHOPPING, MEDICAL, CULTURE, TRANSPORT)
}