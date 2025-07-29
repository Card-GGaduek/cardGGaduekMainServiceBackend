package org.cardGGaduekMainService.cardSummary.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.cardGGaduekMainService.transaction.domain.enums.TransactionCategory;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardCateSummaryVO {

    private TransactionCategory transactionCategory;
    private Long totalSpent;
}
