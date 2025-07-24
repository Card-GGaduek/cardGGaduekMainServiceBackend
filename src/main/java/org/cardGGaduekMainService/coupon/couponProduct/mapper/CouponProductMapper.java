package org.cardGGaduekMainService.coupon.couponProduct.mapper;

import org.apache.ibatis.annotations.Mapper;
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
}