package org.cardGGaduekMainService.coupon.memberCoupon.mapper;

import lombok.extern.log4j.Log4j2;
import org.cardGGaduekMainService.config.RootConfig;
import org.cardGGaduekMainService.coupon.memberCoupon.domain.MemberCouponVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import lombok.extern.log4j.Log4j;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// JUnit 5에서 Spring 테스트 환경을 사용하기 위한 설정입니다.
@ExtendWith(SpringExtension.class)
// Spring 설정 파일의 위치를 지정합니다. DB 연결 및 MyBatis 설정이 포함된 root-context.xml을 사용해야 합니다.
@ContextConfiguration(classes = {RootConfig.class})
// Lombok을 사용하여 로그를 출력하기 위한 설정입니다.
@Log4j2
class MemberCouponMapperTest {

    // @Autowired를 통해 root-context.xml에 설정된 MemberCouponMapper Bean을 주입받습니다.
    @Autowired
    private MemberCouponMapper memberCouponMapper;

    @Test
    @DisplayName("JOIN을 통해 회원의 쿠폰 상세 정보 목록을 정확히 조회해야 한다")
    void selectCouponsWithProductInfo_test() {
        // given (준비): 테스트할 회원의 ID를 지정합니다.
        // 이 테스트는 DB에 member_id가 1인 회원의 쿠폰 데이터가 이미 존재한다고 가정합니다.
        Long testMemberId = 1L;

        // when (실행): 테스트하려는 Mapper 메소드를 호출합니다.
        List<MemberCouponVO> resultList = memberCouponMapper.getCouponsWithProductInfo(testMemberId);

        // then (검증): 결과를 확인합니다.
        assertNotNull(resultList, "결과 리스트는 null이 아니어야 합니다.");
        assertFalse(resultList.isEmpty(), "조회된 쿠폰 목록이 비어있으면 안 됩니다. DB에 member_id=1인 쿠폰 데이터가 있는지 확인하세요.");

        // 조회된 내용을 로그로 출력하여 눈으로 직접 확인합니다.
        log.info("===== 조회된 쿠폰 상세 목록 =====");
        log.info("조회된 회원 ID: " + testMemberId);
        log.info("총 쿠폰 개수: " + resultList.size());

        for (MemberCouponVO coupon : resultList) {
            log.info("회원 쿠폰 정보: " + coupon.toString());
            // JOIN된 쿠폰 상품 정보가 null이 아닌지 확인
            assertNotNull(coupon.getCouponProduct(), "JOIN된 CouponProduct 정보가 null이면 안 됩니다.");
            log.info("  ㄴ 쿠폰 상품 정보: " + coupon.getCouponProduct().toString());
        }
        log.info("==============================");

        // 첫 번째 쿠폰의 member_id가 요청한 ID와 일치하는지 추가로 검증합니다.
        assertEquals(testMemberId, resultList.get(0).getMember_id(), "조회된 쿠폰의 회원 ID가 일치해야 합니다.");
    }
}
