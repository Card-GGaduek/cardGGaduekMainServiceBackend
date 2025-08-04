package org.cardGGaduekMainService.cardPerformance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardPerformanceDTO {

    private Long cardId;
    private String yearMonth;
    private String cardProductName;
    private Long requiredMonthlySpent;
    private Long spentAmount;
    private String cardImageUrl;
}
