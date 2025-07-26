package org.cardGGaduekMainService.coupon.couponProduct.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.cardGGaduekMainService.coupon.couponProduct.domain.CouponProductVO;
import org.cardGGaduekMainService.coupon.couponProduct.mapper.CouponProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class CouponProductServiceImpl implements CouponProductService {

    private final CouponProductMapper couponProductMapper;

    @Override
    public CouponProductVO findCouponProductById(Long id) {
        return couponProductMapper.getCouponProductById(id);
    }

    @Override
    public List<CouponProductVO> findAllCouponProducts() {
        return couponProductMapper.getAllCouponProducts();
    }

    //
    @Override
    @Transactional
    public void issueCouponByMissionReward(Long memberId, String missionReward) {
        try {
            // 1. 미션 보상과 일치하는 쿠폰 상품 조회
            Long couponProductId = couponProductMapper.selectCouponProductIdByName(missionReward);

            if (couponProductId == null) {
                log.warn("쿠폰 상품을 찾을 수 없습니다. missionReward: {}", missionReward);
                return;
            }

            // 2. 쿠폰 발급 (스냅샷 방식)
            int result = couponProductMapper.issueCouponFromProduct(memberId, couponProductId);

            if (result > 0) {
                log.info("쿠폰 발급 성공 - memberId: {}, couponProduct: {}", memberId, missionReward);
            } else {
                log.warn("쿠폰 발급 실패 - memberId: {}, couponProduct: {}", memberId, missionReward);
            }

        } catch (Exception e) {
            log.error("쿠폰 발급 중 오류 발생 - memberId: {}, missionReward: {}", memberId, missionReward, e);
            throw e;
        }
    }

    @Override
    public boolean hasCouponForMission(Long memberId, Long missionId) {
        // 해당 미션으로 이미 쿠폰을 발급받았는지 확인하는 로직
        // (추후 중복 발급 방지용)
        return couponProductMapper.existsCouponByMemberIdAndMissionId(memberId, missionId) > 0;
    }
}
