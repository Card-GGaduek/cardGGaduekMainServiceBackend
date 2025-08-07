package org.cardGGaduekMainService.cardRecommend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardBenefitDTO {

    private Long cardProductId;
    private String cardProductName;
    private boolean owned;
    private boolean meetsRequiredSpend;
    private BenefitByStoreDTO benefitByStore;
    // 한 카드의 총 예상 캐시백 금액
    private int totalBenefit;
}
