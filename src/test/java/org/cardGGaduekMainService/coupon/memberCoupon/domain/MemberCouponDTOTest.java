//package org.cardGGaduekMainService.coupon.memberCoupon.domain;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import java.time.LocalDateTime;
//import static org.junit.jupiter.api.Assertions.*;
//
//class MemberCouponDTOTest {
//
//    @Test
//    @DisplayName("Getter와 Setter가 정상적으로 동작해야 한다")
//    void testGettersAndSetters() {
//        // given - 테스트 데이터 준비
//        MemberCouponVO coupon = new MemberCouponVO();
//        Long id = 1L;
//        Long memberId = 100L;
//        Long couponProductId = 50L;
//        Long statusCodeId = 1L;
//        LocalDateTime now = LocalDateTime.now();
//
//        // when - Setter 메소드 호출
//        coupon.setId(id);
//        coupon.setMember_id(memberId);
//        coupon.setCoupon_product_id(couponProductId);
//        coupon.setStatus_code_id(statusCodeId);
//        coupon.setIssued_at(now);
//
//        // then - Getter 메소드로 값 검증
//        assertEquals(id, coupon.getId(), "ID 값이 일치해야 합니다.");
//        assertEquals(memberId, coupon.getMember_id(), "Member ID 값이 일치해야 합니다.");
//        assertEquals(couponProductId, coupon.getCoupon_product_id(), "Coupon Product ID 값이 일치해야 합니다.");
//        assertEquals(statusCodeId, coupon.getStatus_code_id(), "Status Code ID 값이 일치해야 합니다.");
//        assertEquals(now, coupon.getIssued_at(), "Issued At 값이 일치해야 합니다.");
//    }
//
//    @Test
//    @DisplayName("equals와 hashCode가 동일한 객체에 대해 true를 반환해야 한다")
//    void testEqualsAndHashCode_SameObject() {
//        // given - 동일한 값을 가진 두 객체를 기본 생성자와 setter로 생성
//        LocalDateTime now = LocalDateTime.now();
//
//        MemberCouponVO coupon1 = new MemberCouponVO();
//        coupon1.setId(1L);
//        coupon1.setMember_id(100L);
//        coupon1.setCoupon_product_id(50L);
//        coupon1.setStatus_code_id(1L);
//        coupon1.setIssued_at(now);
//
//        MemberCouponVO coupon2 = new MemberCouponVO();
//        coupon2.setId(1L);
//        coupon2.setMember_id(100L);
//        coupon2.setCoupon_product_id(50L);
//        coupon2.setStatus_code_id(1L);
//        coupon2.setIssued_at(now);
//
//        // then - equals와 hashCode 결과 검증
//        assertEquals(coupon1, coupon2, "모든 필드가 동일한 객체는 equals 결과가 true여야 합니다.");
//        assertEquals(coupon1.hashCode(), coupon2.hashCode(), "모든 필드가 동일한 객체는 hashCode 값이 같아야 합니다.");
//    }
//
//    @Test
//    @DisplayName("equals와 hashCode가 다른 객체에 대해 false를 반환해야 한다")
//    void testEqualsAndHashCode_DifferentObject() {
//        // given - 다른 값을 가진 두 객체를 기본 생성자와 setter로 생성
//        LocalDateTime now = LocalDateTime.now();
//
//        MemberCouponVO coupon1 = new MemberCouponVO();
//        coupon1.setId(1L);
//        coupon1.setMember_id(100L);
//        coupon1.setCoupon_product_id(50L);
//        coupon1.setStatus_code_id(1L);
//        coupon1.setIssued_at(now);
//
//        MemberCouponVO coupon3 = new MemberCouponVO();
//        coupon3.setId(2L); // ID가 다름
//        coupon3.setMember_id(100L);
//        coupon3.setCoupon_product_id(50L);
//        coupon3.setStatus_code_id(1L);
//        coupon3.setIssued_at(now);
//
//        // then - equals 결과 검증
//        assertNotEquals(coupon1, coupon3, "필드가 하나라도 다른 객체는 equals 결과가 false여야 합니다.");
//    }
//
//    @Test
//    @DisplayName("toString 메소드가 객체의 상태를 문자열로 반환해야 한다")
//    void testToString() {
//        // given - 테스트 객체 생성
//        MemberCouponVO coupon = new MemberCouponVO();
//        coupon.setId(1L);
//        coupon.setMember_id(100L);
//
//        // when - toString 메소드 호출
//        String couponString = coupon.toString();
//
//        // <<<<< 내용 출력 추가 >>>>>
//        System.out.println("toString() 결과: " + couponString);
//
//        // then - 결과 문자열 검증
//        assertNotNull(couponString, "toString 결과는 null이 아니어야 합니다.");
//        assertTrue(couponString.contains("id=1"), "toString 결과에 ID 값이 포함되어야 합니다.");
//        assertTrue(couponString.contains("member_id=100"), "toString 결과에 Member ID 값이 포함되어야 합니다.");
//    }
//}
