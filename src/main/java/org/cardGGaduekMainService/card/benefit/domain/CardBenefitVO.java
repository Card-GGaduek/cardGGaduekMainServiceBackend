package org.cardGGaduekMainService.card.benefit.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardBenefitVO {
    private Long id;
    private Long card_productId;
    private Long storeCategory;
    private String storeName;
    private String benefitType;
    private String valueType;
    private BigDecimal rateValue;
    private BigDecimal amountValue;
    private String description;
}
