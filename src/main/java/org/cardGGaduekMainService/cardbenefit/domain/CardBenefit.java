package org.cardGGaduekMainService.cardbenefit.domain;

import lombok.Data;

@Data
public class CardBenefit {
    private Long benefitId;
    private Long merchantId;
    private String cardName;
    private int discountRate;
    private String benefitDetail;
}
