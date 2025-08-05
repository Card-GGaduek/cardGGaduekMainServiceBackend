package org.cardGGaduekMainService.storeBenefit.enums;

public enum BenefitType {
    DISCOUNT("discount"),
    POINT("point"),
    CASHBACK("cashback"),
    UNKNOWN("unknown");

    private String value;

    BenefitType(String value) {
        this.value = value;
    }
}
