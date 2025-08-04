package org.cardGGaduekMainService.cardRecommend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecommendDTO {

    private Long memberId;
    private CombinationDTO current;
    private CombinationDTO recommendation;
    private int additionalSaving;
    private String predictionModel;   // "RF"
}
