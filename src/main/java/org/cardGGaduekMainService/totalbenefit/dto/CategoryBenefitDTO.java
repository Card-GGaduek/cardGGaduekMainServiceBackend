package org.cardGGaduekMainService.totalbenefit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryBenefitDTO {
    private String category;
    private Long amount;
}
