package org.cardGGaduekMainService.cardSummary.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardSummaryVO {

    private String yearMonth;
    private Long totalSpent;

}
