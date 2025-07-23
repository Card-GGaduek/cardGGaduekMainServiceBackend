package org.cardGGaduekMainService.card.benefit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardBenefitDTO {

//    private Long id;
//    private Long card_product_id;
//    private String store_name;
    private String cardName;
    private Double discount_rate;
    private String description;
}
