package org.cardGGaduekMainService.cardPerformance.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardPerformanceVO {

    private Long cardId;
    private String cardProductName;
    private Long requiredMonthlySpent;
    private Long spentAmount;
    private String cardImageUrl;

}
