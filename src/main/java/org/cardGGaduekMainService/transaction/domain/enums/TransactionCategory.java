package org.cardGGaduekMainService.transaction.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum TransactionCategory {
    UNKNOWN("그 외"),
    FOOD("식비"),
    SHOPPING("쇼핑"),
    MEDICAL("의료비"),
    TRANSPORT("교통비"),
    CULTURE("문화"),
    ACCOMODATION("숙박");

    private final String displayName;

    TransactionCategory(String displayName) {
        this.displayName = displayName;
    }

    public static TransactionCategory fromDisplayName(String name) {
        return Arrays.stream(values())
                .filter(c -> c.displayName.equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown category: " + name));
    }

    @JsonCreator
    public static TransactionCategory fromName(String name) {
        return Arrays.stream(values())
                .filter(c -> c.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown category enum: " + name));
    }
}
