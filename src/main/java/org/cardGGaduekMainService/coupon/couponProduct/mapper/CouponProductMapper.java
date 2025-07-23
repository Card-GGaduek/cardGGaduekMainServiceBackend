package org.cardGGaduekMainService.coupon.couponProduct.mapper;

import org.cardGGaduekMainService.coupon.couponProduct.domain.CouponProductVO;

import java.util.List;

public interface CouponProductMapper {
    /**
     * 새로운 쿠폰 상품을 데이터베이스에 등록합니다.
     * @param vo 등록할 쿠폰 상품 정보
     * @return 영향을 받은 행의 수
     */
    int insertCouponProduct(CouponProductVO vo);

    /**
     * ID로 특정 쿠폰 상품을 조회합니다.
     * @param id 조회할 쿠폰 상품의 ID
     * @return 조회된 쿠폰 상품 정보
     */
    CouponProductVO selectCouponProductById(int id);

    /**
     * 모든 쿠폰 상품 목록을 조회합니다.
     * @return 쿠폰 상품 전체 목록
     */
    List<CouponProductVO> selectAllCouponProducts();
}