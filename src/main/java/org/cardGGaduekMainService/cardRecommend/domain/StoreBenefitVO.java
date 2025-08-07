package org.cardGGaduekMainService.cardRecommend.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cardGGaduekMainService.cardRecommend.domain.enums.BenefitType;
import org.cardGGaduekMainService.cardRecommend.domain.enums.ValueType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreBenefitVO {

    private Long id;
    private Long cardProductId;
    private String storeName;
    private String storeCategory;
    private BenefitType benefitType;
    private ValueType valueType;
    private Double rateValue;
    private Integer amountValue;
    private String description;

}
