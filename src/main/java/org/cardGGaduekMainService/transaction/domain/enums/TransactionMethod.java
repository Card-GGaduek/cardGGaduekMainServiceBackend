package org.cardGGaduekMainService.transaction.domain.enums;

import lombok.Getter;

@Getter
public enum TransactionMethod {
    CREDIT("신용카드");

    private final String displayName;

    TransactionMethod(String displayName) {
        this.displayName = displayName;
    }
}
