package org.cardGGaduekMainService.transaction.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardTransactionsDTO {
    private Long cardId;        // 사용자 보유 카드 ID
    private Long cardProductId; // 전체 카드 상품의 카드 ID
    private String cardName;
    private List<CTransactionDTO> transactions;
}
