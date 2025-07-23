package org.cardGGaduekMainService.coupon.couponProduct.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CouponProductVO {
    private int id;
    private LocalDateTime issued_at;
    private LocalDateTime expired_at;
    private String coupon_name;
    private int coupon_quantity;
    private String description;
}
