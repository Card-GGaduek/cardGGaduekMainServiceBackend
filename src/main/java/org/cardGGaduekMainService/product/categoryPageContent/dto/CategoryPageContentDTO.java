package org.cardGGaduekMainService.product.categoryPageContent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryPageContentDTO {
    private Long id;
    private String categoryName;
    private Long cardProductId;
    private String title;
    private String description;
    private String imageUrl;
    private String linkUrl;
    private String cardName;
    private BigDecimal discountRate;
    private BigDecimal price;
    private int expectedBenefitAmount;
    private String valueType;
    private BigDecimal rateValue;
    private Long amountValue;
}
