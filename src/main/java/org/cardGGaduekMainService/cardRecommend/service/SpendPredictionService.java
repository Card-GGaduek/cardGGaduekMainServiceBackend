package org.cardGGaduekMainService.cardRecommend.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SpendPredictionService {

    /**
     * RF(Random-Forest)로 예측한 다음 달 예상 지출액(원) – 편의점·카페·놀이공원 등
     */
    public Map<String, Integer> predict(Long memberId) {

        return Map.ofEntries(
                Map.entry("CGV", 16),
                Map.entry("GS25", 1300),
                Map.entry("CU", 7538),
                Map.entry("KFC", 0),
                Map.entry("그 외", 776379),

                Map.entry("롯데리아", 7),
                Map.entry("롯데월드", 9),
                Map.entry("맥도날드", 296),
                Map.entry("메가박스", 9),
                Map.entry("미니스톱", 0),

                Map.entry("버거킹", 0),
                Map.entry("세븐일레븐", 1643),
                Map.entry("스타벅스", 0),
                Map.entry("에버랜드", 735),
                Map.entry("이디야", 442),

                Map.entry("이마트24", 3804),
                Map.entry("커피빈", 3288),
                Map.entry("투썸플레이스", 12876),
                Map.entry("풀바셋", 0),

                // 범주형 합산 항목
                Map.entry("GS충전소", 0),
                Map.entry("GS칼텍스", 0),
                Map.entry("SK충전소", 0),
                Map.entry("S-OIL", 0)

        );
    }
}
