package org.cardGGaduekMainService.coupon.couponProduct.service;

public interface CouponProductService {

    void issueCouponByMissionReward(Long memberId, String missionReward);
    boolean hasCouponForMission(Long memberId, Long missionId);
}
