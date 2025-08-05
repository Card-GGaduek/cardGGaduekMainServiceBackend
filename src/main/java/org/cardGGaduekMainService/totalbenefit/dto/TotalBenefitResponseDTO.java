package org.cardGGaduekMainService.totalbenefit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cardGGaduekMainService.totalbenefit.dto.CategoryBenefitDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalBenefitResponseDTO {
    private Long totalBenefitAmount;
    private List<CategoryBenefitDTO> categoryBenefits;
}
