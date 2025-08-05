package org.cardGGaduekMainService.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cardGGaduekMainService.coupon.memberCoupon.domain.MemberCouponVO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyPageDTO {

    private String name;
    private String email;
    private String phone;
    private List<MyCouponDTO> couponList;
}
