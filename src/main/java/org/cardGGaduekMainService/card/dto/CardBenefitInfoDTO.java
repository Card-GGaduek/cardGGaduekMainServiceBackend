package org.cardGGaduekMainService.card.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardBenefitInfoDTO {
    private String benefitCategory;
    private String storeName;
    private Integer discountRate;
    private String description;
}
