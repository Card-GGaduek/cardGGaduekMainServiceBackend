package org.cardGGaduekMainService.transaction.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum TransactionStatus {
    APPROVED(1L, "승인"),
    CANCELED(2L, "취소");

    private final Long code;
    private final String description;

    TransactionStatus(Long code, String description) {
        this.code = code;
        this.description = description;
    }

    @JsonCreator
    public static TransactionStatus fromString(String name) {
        return Arrays.stream(values())
                .filter(status -> status.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown status: " + name));
    }
}

