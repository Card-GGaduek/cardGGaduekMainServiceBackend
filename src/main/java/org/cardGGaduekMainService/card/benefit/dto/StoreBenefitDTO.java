package org.cardGGaduekMainService.card.benefit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreBenefitDTO {
    private Long id;
    private Long cardProductId;
    private String storeCategory;
    private String valueType;
    private BigDecimal rateValue;
    private Long amountValue;
}
