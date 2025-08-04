package org.cardGGaduekMainService.card.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cardGGaduekMainService.card.benefit.dto.CardBenefitDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardBackDTO {
    private String cardProductName;
    private String bankName;
    private List<CardBenefitInfoDTO> benefits;
}
