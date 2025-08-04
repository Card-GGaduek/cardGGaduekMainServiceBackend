package org.cardGGaduekMainService.cardbenefit.domain;

import lombok.Data;

@Data
public class Benefit {
    private Long benefitId;
    private String benefitName;
    private Double discountRate;
    private String conditions;
    private String description;
}

