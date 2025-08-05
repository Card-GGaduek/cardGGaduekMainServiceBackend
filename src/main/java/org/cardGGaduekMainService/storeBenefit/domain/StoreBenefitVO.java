package org.cardGGaduekMainService.storeBenefit.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cardGGaduekMainService.storeBenefit.enums.BenefitType;
import org.cardGGaduekMainService.storeBenefit.enums.StoreCategory;
import org.cardGGaduekMainService.storeBenefit.enums.ValueType;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreBenefitVO {
    private Long id;
    private Long cardProductId;
    private StoreCategory storeCategory;
    private String storeName;
    private BenefitType benefitType;
    private ValueType valueType;
    private BigDecimal rateValue;
    private Integer amountValue;
    private String description;
}
