package org.cardGGaduekMainService.cardSummary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardSummaryDTO {

    private String yearMonth;
    private Long totalSpent;
    private Long spendingDiff;
}
