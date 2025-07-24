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
    //카드아이디
    private Long cardId;
    private String cardName;
    private List<TransactionDTO> transactions;
}
