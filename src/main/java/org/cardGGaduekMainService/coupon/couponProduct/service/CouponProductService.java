package org.cardGGaduekMainService.coupon.couponProduct.service;

import org.cardGGaduekMainService.coupon.couponProduct.domain.CouponProductVO;

import java.util.List;

public interface CouponProductService {

    /**
     * 새로운 쿠폰 상품을 생성합니다.
     * @param vo 생성할 쿠폰 상품 정보
     */
    void createCouponProduct(CouponProductVO vo);

    /**
     * ID로 특정 쿠폰 상품을 조회합니다.
     * @param id 조회할 쿠폰 상품의 ID
     * @return 조회된 쿠폰 상품 정보
     */
    CouponProductVO findCouponProductById(int id);

    /**
     * 모든 쿠폰 상품 목록을 조회합니다.
     * @return 쿠폰 상품 전체 목록
     */
    List<CouponProductVO> findAllCouponProducts();
}
