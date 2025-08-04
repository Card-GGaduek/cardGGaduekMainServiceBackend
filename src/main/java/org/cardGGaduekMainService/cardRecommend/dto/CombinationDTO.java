package org.cardGGaduekMainService.cardRecommend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CombinationDTO {

    private List<CardBenefitDTO> cards;
    private int aggregateBenefit;
}
