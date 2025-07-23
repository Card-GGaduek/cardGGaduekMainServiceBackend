package org.cardGGaduekMainService.coupon.couponProduct.service;

import org.cardGGaduekMainService.coupon.couponProduct.domain.CouponProductVO;
import org.cardGGaduekMainService.coupon.couponProduct.mapper.CouponProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CouponProductServiceImpl implements CouponProductService {
    // Spring이 root-context.xml 등의 설정에 따라 Mapper 인터페이스의 구현체를 주입해 줍니다.
    @Autowired
    private CouponProductMapper couponProductMapper;

    @Override
    public void createCouponProduct(CouponProductVO vo) {
        // 발급 시작일은 서비스 로직에서 현재 시간으로 설정
        vo.setIssued_at(LocalDateTime.now());
        couponProductMapper.insertCouponProduct(vo);
    }

    @Override
    public CouponProductVO findCouponProductById(int id){
        return couponProductMapper.selectCouponProductById(id);
    }

    @Override
    public List<CouponProductVO> findAllCouponProducts() {
        return couponProductMapper.selectAllCouponProducts();
    }
}
