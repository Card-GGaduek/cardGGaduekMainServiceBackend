package org.cardGGaduekMainService.cardRecommend.dto;

import java.util.Map;

//매장별 예상 캐시백 금액
// 매장별 예상 캐시백 금액을 나타내는 DTO 클래스입니다.(db를 돌면서 예측소비에 대해 캐시백 계산한값)
public record BenefitByStoreDTO(Map<String,Integer> map) {
}
