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
    private String storeName;         // GS25 …
    private String storeCategory;     // 편의점, 마트, 커피, 패스트푸드 등
    private BenefitType benefitType;  // DISCOUNT/CASHBACK/POINT
    private ValueType valueType;      // PERCENT/AMOUNT
    private Double rateValue;         // %  (nullable)
    private Integer amountValue;    // 원 (nullable)
    private String description;
}
