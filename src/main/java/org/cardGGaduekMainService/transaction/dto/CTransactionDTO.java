package org.cardGGaduekMainService.transaction.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CTransactionDTO {

    private Long id;
    private Long cardId;
    private String storeName;
    private BigDecimal amount;
    @JsonIgnore
    private String cardName;
    private String transDate;
}
