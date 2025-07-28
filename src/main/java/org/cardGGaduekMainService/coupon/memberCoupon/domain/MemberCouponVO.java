package org.cardGGaduekMainService.coupon.memberCoupon.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cardGGaduekMainService.coupon.couponProduct.domain.CouponProductVO;

import java.math.BigDecimal;
import java.math.BigInteger;
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
    private LocalDateTime expiredAt;
    private String couponStatus;
    private BigDecimal discountValue;
    private CouponProductVO couponProduct;
}
