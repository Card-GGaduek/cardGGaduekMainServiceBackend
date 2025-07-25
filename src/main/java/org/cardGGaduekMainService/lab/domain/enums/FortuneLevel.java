package org.cardGGaduekMainService.lab.domain.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum FortuneLevel {
    F10(10, "오늘은 지갑 꽉 닫으세요!"),
    F20(20, "지금은 절약에 집중해야 할 때..."),
    F30(30, "소비보다 저축 어떤가요?"),
    F40(40, "구매 전 한 번 더 생각해보기!"),
    F50(50, "평소같은 소비습관 유지하기!"),
    F60(60, "소소한 것으로 행복해지기"),
    F70(70, "오늘은 위시리스트 중 하나를 이뤄볼까요?"),
    F80(80, "장바구니 가볍게 비워볼까요?"),
    F90(90, "먹고 싶은 거 다 먹자~"),
    F100(100, "오늘은 FLEX DAY🔥");

    private final int index;
    private final String message;

    FortuneLevel(int index, String message) {
        this.index = index;
        this.message = message;
    }

    // index로 enum 가져오기
    public static FortuneLevel fromIndex(int index) {
        if (index % 10 != 0 || index < 10 || index > 100) {
            throw new IllegalArgumentException("Invalid fortune index: " + index);
        }

        return Arrays.stream(FortuneLevel.values())
                .filter(level -> level.index == index)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No matching fortune level for index: " + index));
    }

    public static FortuneLevel getRandom() {
        FortuneLevel[] values = FortuneLevel.values();
        return values[(int) (Math.random() * values.length)];
    }

}
