package org.cardGGaduekMainService.cardRecommend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardRecommendDTO {

    private Long memberId;
    //현재 카드 조합
    private CardCombinationDTO current;
    //추천 카드 조합
    private CardCombinationDTO recommendation;
    //추가 절약액
    private int additionalSaving;
    //예측 모델
    private String predictionModel;
}

