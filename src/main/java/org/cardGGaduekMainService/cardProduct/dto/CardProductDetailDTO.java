package org.cardGGaduekMainService.cardProduct.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardProductDetailDTO {
    private Long id;
    private String cardProductName;
    private String cardImageUrl;
    private int annualFee;
    private int requiredMonthlySpent;
    private String cardApplyUrl;
    private List<BenefitDTO> benefits;
}
