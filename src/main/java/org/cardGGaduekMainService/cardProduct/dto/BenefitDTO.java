package org.cardGGaduekMainService.cardProduct.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BenefitDTO {
    private String storeCategory;
    private String storeName;
    private String description;
}
