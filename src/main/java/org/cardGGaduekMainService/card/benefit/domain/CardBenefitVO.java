package org.cardGGaduekMainService.card.benefit.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardBenefitVO {
    private Long id;
    private Long card_product_id;
    private Long benefit_category_id;
    private String store_name;
    private Double discount_rate;
    private String description;

}
