package org.cardGGaduekMainService.card.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cardGGaduekMainService.storeBenefit.dto.StoreBenefitDTO;
import org.springframework.lang.Nullable;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyCardDTO {
    private Long cardId;
    private String cardNumber;
    private Long cardProductId;
    private String organizationCode;
    private String organizationName;
    private String cardProductName;
    private String cardImageUrl;
    @Nullable
    private String customCardImageUrl;
    private int requiredMonthlySpent;
    private List<StoreBenefitDTO> storeBenefitList;
}
