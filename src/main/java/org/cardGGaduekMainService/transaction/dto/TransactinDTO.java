package org.cardGGaduekMainService.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactinDTO {
    private Long id;                    // 거래고유ID
    private Long userId;                // 사용자 ID
    private Long cardId;                // 카드ID
    private BigDecimal amount;          // 결제 금액
    private Enum transactionCategory;   // 결제 카테고리 (-> enum 직접 만들기)
    private Enum subcategoryCodeId ;    // 세부 카테고리 (-> enum 직접 만들기)
    private boolean status;             // 결제상태
    private String paymentMethod;       // 결제방식
    private LocalDateTime date;         // 결제일시
    private String approvalCode;        // 승인번호
    private String memo;                // 메모
}
