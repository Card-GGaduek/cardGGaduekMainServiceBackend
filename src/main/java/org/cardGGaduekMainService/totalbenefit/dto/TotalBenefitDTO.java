package org.cardGGaduekMainService.totalbenefit.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TotalBenefitDTO {
    private int totalBenefit;

    private List<CategoryBenefit> categoryBenefits;

    @Getter @Setter
    public static class CategoryBenefit {
        private String category;
        private int amount;
    }
}