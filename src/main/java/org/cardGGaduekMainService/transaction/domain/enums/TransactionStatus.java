package org.cardGGaduekMainService.transaction.domain.enums;

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

    public static TransactionStatus fromCode(Long code) {
        return Arrays.stream(values())
                .filter(status -> status.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown status code: " + code));
    }
}

