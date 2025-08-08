package org.cardGGaduekMainService.cardRecommend.util;

import org.cardGGaduekMainService.cardRecommend.domain.StoreBenefitVO;

public final class BenefitUtil {

    private BenefitUtil() {}
    public static int toWon(int spend, StoreBenefitVO storeBenefitVO) {
        return switch (storeBenefitVO.getValueType()) {
            case PERCENT -> (int)Math.round(spend * storeBenefitVO.getRateValue() / 100.0);
            case AMOUNT  -> Math.min(storeBenefitVO.getAmountValue(), spend);
        };
    }
}
