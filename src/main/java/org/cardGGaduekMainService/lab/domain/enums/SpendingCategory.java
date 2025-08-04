package org.cardGGaduekMainService.lab.domain.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum SpendingCategory {
    NON("이번 달은 어떤 모습일까?", "non"),
    FOOD("이번 달은 푸드파이터", "food"),
    SHOPPING("이번 달은 트렌드세터", "shopping"),
    MEDICAL("이번 달은 조심조심", "medical"),
    TRANSPORT("이번 달은 나들이러버", "transport"),
    CULTURE("이번 달은 추억가득", "culture");

    private final String resultTitle;
    private final String imageFileName;

    SpendingCategory(String resultTitle, String imageFileName) {
        this.resultTitle = resultTitle;
        this.imageFileName = imageFileName;
    }

    public static SpendingCategory fromName(String name) {
        return Arrays.stream(values())
                .filter(c -> c.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown category: " + name));
    }

    // TransactionCategory -> SpendingCategory 매핑
    public static SpendingCategory fromTransactionCategory(Enum<?> transactionCategory) {
        if (transactionCategory == null) return NON;

        try {
            return SpendingCategory.valueOf(transactionCategory.name());
        } catch (IllegalArgumentException e) {
            return NON;
        }
    }

    public static SpendingCategory fromCode(String code) {
        for (SpendingCategory sc : values()) {
            if (sc.name().equalsIgnoreCase(code)) {
                return sc;
            }
        }
        return NON;
    }
}
