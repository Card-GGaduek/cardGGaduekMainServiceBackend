package org.cardGGaduekMainService.storeBenefit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cardGGaduekMainService.storeBenefit.enums.BenefitType;
import org.cardGGaduekMainService.storeBenefit.enums.ValueType;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreBenefitDTO {
    private String storeName;
    private String storeCategory;
    private BenefitType benefitType;
    private ValueType valueType;
    private BigDecimal rateValue;
    private Integer amountValue;
    private String description;

}
