package org.cardGGaduekMainService.transaction.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cardGGaduekMainService.transaction.domain.TransactionVO;
import org.cardGGaduekMainService.transaction.domain.enums.TransactionCategory;
import org.cardGGaduekMainService.transaction.domain.enums.TransactionMethod;
import org.cardGGaduekMainService.transaction.domain.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDTO {

    private Long id;
    private Long cardId;
    private String storeName;
    private BigDecimal amount;
    @JsonIgnore
    private String cardName;
    @JsonIgnore
    private String transDate;

    // 거래 생성용 추가 필드
    private Long memberId;
    private Long storeId;
    private Long productId;
    private Long couponId;
    private TransactionCategory transactionCategory;
    private TransactionStatus transactionStatus;
    private TransactionMethod transactionMethod;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    private String approvalCode;
    private String memo;

    // DTO -> VO
    public TransactionVO toEntity() {
        return TransactionVO.builder()
                .memberId(memberId)
                .cardId(cardId)
                .storeName(storeName)
                .productId(productId)
                .couponId(couponId)
                .amount(amount)
                .transactionCategory(transactionCategory)
                .transactionStatus(transactionCategory)
                .transactionMethod(transactionCategory)
                .date(date)
                .approvalCode(approvalCode)
                .memo(memo)
                .build();
    }
}
