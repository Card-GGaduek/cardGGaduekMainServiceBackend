package org.cardGGaduekMainService.transaction.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionVO {
    private Long id;                    // 거래고유ID
    private Long cardId;                // 카드ID
    private BigDecimal amount;          // 결제 금액
    private Enum transactionCategory;   // 결제 카테고리
    private Enum subcategoryCodeId;     // 세부 카테고리
    private boolean status;             // 결제상태
    private String paymentMethod;       // 결제방식
    private Date date;                  // 결제일시
    private String approvalCode;        // 승인번호
    private String memo;                // 메모
}
