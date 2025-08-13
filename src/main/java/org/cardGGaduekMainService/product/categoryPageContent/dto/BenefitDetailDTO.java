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
public class BenefitDetailDTO {
    private Long id;
    private Long cardProductId;
    private String cardName;
    private String storeName;
    private String description;

    private String valueType;
    private BigDecimal rateValue;
    private BigDecimal amountValue;

    private Long contentId;
    private String imageUrl;
    private String linkUrl;
    private BigDecimal price;
}
