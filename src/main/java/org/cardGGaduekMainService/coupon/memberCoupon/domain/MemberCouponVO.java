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
    private Long member_id;
    private String coupon_name;
    private Long coupon_product_id;
    private BigDecimal discount_value;
    private int coupon_quantity;
    private Long coupon_category;
    private boolean status_code_id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime issued_at;

    private CouponProductVO couponProduct;
}
