package org.cardGGaduekMainService.cardRecommend.util;

import org.cardGGaduekMainService.cardRecommend.domain.StoreBenefitVO;

public final class BenefitUtil {

    private BenefitUtil() {}
    public static int toWon(int spend, StoreBenefitVO r) {
        return switch (r.getValueType()) {
            case PERCENT -> (int)Math.round(spend * r.getRateValue() / 100.0);
            case AMOUNT  -> Math.min(r.getAmountValue(), spend);
        };
    }
}
