package org.cardGGaduekMainService.card.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cardGGaduekMainService.storeBenefit.dto.StoreBenefitDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyCardDTO {
    private Long cardId;
    private String cardNumber;
    private Long cardProductId;
    private String cardProductName;
    private String cardImageUrl;
    private int requiredMonthlySpent;
    private List<StoreBenefitDTO> storeBenefitList;
}
