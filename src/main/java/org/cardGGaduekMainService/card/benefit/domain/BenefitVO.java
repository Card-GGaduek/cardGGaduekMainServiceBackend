package org.cardGGaduekMainService.card.benefit.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BenefitVO {
    private Long id;
    private Long card_product_id;
    private String conditions;
    private Double discount_rate;
    private String description;

}
