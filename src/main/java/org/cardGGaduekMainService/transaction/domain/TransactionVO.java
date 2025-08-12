package org.cardGGaduekMainService.transaction.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cardGGaduekMainService.transaction.domain.enums.TransactionCategory;
import org.cardGGaduekMainService.transaction.domain.enums.TransactionMethod;
import org.cardGGaduekMainService.transaction.domain.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionVO {

    private Long id;
    private Long memberId;
    private Long cardId;
    private String storeName;
    private String storeCategory;
    private BigDecimal  amount;
    private TransactionCategory transactionCategory;
    private TransactionStatus transactionStatus;
    private TransactionMethod transactionMethod;
    private LocalDateTime date;
    private String approvalCode;
    private String memo;

}
