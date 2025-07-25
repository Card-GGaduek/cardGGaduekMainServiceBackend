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
    private Long id;                 // 거래고유ID
    private Long memberId;              // 회원ID
    private Long cardId;                // 카드ID
    private Long storeId;               // 가맹점ID
    private Long productId;             // 상품ID
    private Long couponId;              // 쿠폰ID
    private BigDecimal amount;          // 결제 금액
    private TransactionCategory transactionCategory;   // 결제 카테고리 (FOOD, SHOPPING, MEDICAL, TRANSPORT, CULTURE)
    private TransactionStatus transactionStatus;       // 결제상태 (APPROVED-승인, CANCELED-취소)
    private TransactionMethod transactionMethod;       // 결제방식
    private LocalDateTime date;         // 결제일시
    private String approvalCode;        // 승인번호
    private String memo;                // 메모
}
