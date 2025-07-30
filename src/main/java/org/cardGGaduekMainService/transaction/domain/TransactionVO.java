package org.cardGGaduekMainService.transaction.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cardGGaduekMainService.transaction.domain.enums.TransactionCategory;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionVO {

    private Long id;
    private Long memberId;
    private Long cardId;
    private String storeName;
    private Long productId;
    private Long couponId;
    private BigDecimal  amount;
    private TransactionCategory transactionCategory;
    private TransactionCategory transactionStatus;
    private TransactionCategory transactionMethod;
    private LocalDateTime date;
    private String approvalCode;
    private String memo;


}
