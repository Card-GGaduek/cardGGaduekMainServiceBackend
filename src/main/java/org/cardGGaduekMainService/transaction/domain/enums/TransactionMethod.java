package org.cardGGaduekMainService.transaction.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum TransactionMethod {
    CREDIT("신용카드");

    private final String displayName;

    TransactionMethod(String displayName) {
        this.displayName = displayName;
    }

    @JsonCreator
    public static TransactionMethod from(String name) {
        return Arrays.stream(values())
                .filter(m -> m.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown transaction method: " + name));
    }
}
