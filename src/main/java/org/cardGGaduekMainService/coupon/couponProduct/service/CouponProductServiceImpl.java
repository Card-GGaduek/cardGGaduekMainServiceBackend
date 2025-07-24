package org.cardGGaduekMainService.coupon.couponProduct.service;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.coupon.couponProduct.domain.CouponProductVO;
import org.cardGGaduekMainService.coupon.couponProduct.mapper.CouponProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
}
