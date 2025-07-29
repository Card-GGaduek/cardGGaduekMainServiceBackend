package org.cardGGaduekMainService.coupon.couponProduct.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cardGGaduekMainService.coupon.couponProduct.domain.CouponProductVO;

import java.util.List;

@Mapper
public interface CouponProductMapper {
    /**
     * ID로 특정 쿠폰 상품을 조회합니다.
     * @param id 조회할 쿠폰 상품의 ID
     * @return 조회된 쿠폰 상품 정보
     */
    CouponProductVO getCouponProductById(Long id);

    /**
     * 모든 쿠폰 상품 목록을 조회합니다.
     * @return 쿠폰 상품 전체 목록
     */
    List<CouponProductVO> getAllCouponProducts();

    //
    // 쿠폰 상품명으로 ID 조회
    Long selectCouponProductIdByName(@Param("couponName") String couponName);

    // 쿠폰 발급 (스냅샷 방식)
    int issueCouponFromProduct(@Param("memberId") Long memberId,
                               @Param("couponProductId") Long couponProductId);

    // 중복 발급 확인 (선택사항)
    int existsCouponByMemberIdAndMissionId(@Param("memberId") Long memberId,
                                           @Param("missionId") Long missionId);
}