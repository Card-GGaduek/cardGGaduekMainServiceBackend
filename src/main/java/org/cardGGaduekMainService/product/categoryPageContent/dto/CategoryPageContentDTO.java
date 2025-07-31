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
    private String title;
    private String description;
    private String imageUrl;
    private String linkUrl;

    private BigDecimal discountRate;
}
