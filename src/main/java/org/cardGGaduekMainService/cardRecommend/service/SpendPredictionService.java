package org.cardGGaduekMainService.cardRecommend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpendPredictionService {

    private final TestFlaskService flaskService;

    /**
     * memberId를 넘겨서 Flask 예측값을 받아오고,
     * Map<String,Object> → Map<String,Integer> 로 변환 후 리턴
     */
    public Map<String,Integer> predict(Long memberId) {
        Map<String,Object> raw = flaskService.fetchRawSpend(memberId);

        return raw.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> {
                            Object v = e.getValue();
                            // JSON 숫자가 Integer, Long, Double 등으로 올 수 있으니 Number로 처리
                            if (v instanceof Number) {
                                return ((Number)v).intValue();
                            } else {
                                return Integer.parseInt(v.toString());
                            }
                        }
                ));
    }


}
