package org.cardGGaduekMainService.cardRecommend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CardCombinationDTO {
    //카드 조합 dto
    private List<CardBenefitDTO> cards;
    // 카드 조합의 총 예상 캐시백 금액
    private int aggregateBenefit;
}
