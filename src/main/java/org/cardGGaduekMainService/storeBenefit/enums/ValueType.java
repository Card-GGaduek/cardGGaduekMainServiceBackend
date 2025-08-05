package org.cardGGaduekMainService.storeBenefit.enums;

public enum ValueType {
    PERCENT("percent"),
    AMOUNT("amount"),
    UNKNOWN("unknown");

    private String value;

    ValueType(String value) {
        this.value = value;
    }
}
