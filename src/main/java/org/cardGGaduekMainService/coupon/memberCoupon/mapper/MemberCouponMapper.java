package org.cardGGaduekMainService.coupon.memberCoupon.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cardGGaduekMainService.coupon.memberCoupon.domain.MemberCouponVO;
import org.cardGGaduekMainService.member.domain.MemberVO;
import org.cardGGaduekMainService.member.dto.MemberCouponDTO;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface MemberCouponMapper {

//    /**
//     * 회원에게 발급된 쿠폰 정보를 데이터베이스에 등록합니다.
//     * @param vo 등록할 회원 쿠폰 정보
//     * @return 영향을 받은 행의 수
//     */
//    int insertMemberCoupon(MemberCouponVO vo);
//
//    /**
//     * ID로 특정 회원 쿠폰을 조회합니다.
//     * @param id 조회할 회원 쿠폰의 ID
//     * @return 조회된 회원 쿠폰 정보
//     */
//    MemberCouponVO getMemberCouponById(int id);
//
//    /**
//     * 특정 회원이 보유한 쿠폰 목록을 조회합니다.
//     * @param params Map 객체 (key: "memberId", value: 회원 ID), (key: "status", value: 사용 상태)
//     * @return 조회된 회원 쿠폰 목록
//     */
//    List<MemberCouponVO> getCouponsByMemberId(Map<String, Object> params);
//
//    /**
//     * 특정 회원 쿠폰의 상태를 '사용 완료'로 변경합니다.
//     * @param id 상태를 변경할 회원 쿠폰의 ID
//     * @return 영향을 받은 행의 수
//     */
//    int updateCouponStatus(int id);
    /**
     * 특정 회원이 보유한 쿠폰 목록을 조회합니다.
     * @param params Map 객체 (key: "memberId", value: 회원 ID), (key: "status", value: 쿠폰 상태 코드 ID - 선택 사항)
     * @return 조회된 회원 쿠폰 목록
     */
//    List<MemberCouponVO> getCouponsByMemberId(Map<String, Object> params);
    List<MemberCouponVO> getCouponsWithProductInfo(Long memberId);
    MemberCouponDTO getMemberWithCouponsByMemberId(Long memberId);

    MemberCouponVO findByMemberIdAndCouponId(
            @Param("memberId") Long memberId,
            @Param("couponProductId") Long couponProductId
    );

    int insertMemberCoupon(
            @Param("memberId") Long memberId,
            @Param("couponProductId") Long couponProductId
    );

}
