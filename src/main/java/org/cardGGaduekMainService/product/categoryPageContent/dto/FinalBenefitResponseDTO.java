package org.cardGGaduekMainService.product.categoryPageContent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinalBenefitResponseDTO {
    private Long id;
    private String title;
    private String imageUrl;
    private String linkUrl;

    private String cardName;
    private String benefitDescription;
    private String valueType;
    private BigDecimal rateValue;
    private BigDecimal amountValue;
    private BigDecimal price;
    private BigDecimal calculatedDiscountAmount;
}
