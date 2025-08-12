package org.cardGGaduekMainService.cardRecommend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SpendPredictionService {

    private final TestFlaskService testFlaskService;
    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;

    private static final ZoneId KST = ZoneId.of("Asia/Seoul");
    private static final DateTimeFormatter YM = DateTimeFormatter.ofPattern("yyyyMM");
    private static final Duration TTL = Duration.ofHours(24);

    public Map<String,Integer> predict(Long memberId) {
        String yearMonth = YearMonth.now(KST).format(YM);
        String key  = "pred:%d:%s".formatted(memberId, yearMonth);
        String lkey = "lock:" + key;

        try {
            String cached = stringRedisTemplate.opsForValue().get(key);
            if (cached != null) {
                return objectMapper.readValue(cached, new TypeReference<Map<String,Integer>>() {});
            }

        } catch (Exception e) {
            stringRedisTemplate.delete(key);
        }

        // 2) 스탬피드 방지용 락 (5초)
        boolean gotLock = Boolean.TRUE.equals(
                stringRedisTemplate.opsForValue().setIfAbsent(lkey, "1", Duration.ofSeconds(5))
        );

        if (gotLock) {
            try {
                //모델접근
                Map<String,Integer> data = toIntMap(testFlaskService.getSpend(memberId));
                // 캐시 저장
                stringRedisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(data), TTL);
                return data;
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                stringRedisTemplate.delete(lkey);
            }
        }

        // 3) 누가 계산중이면 0.8초까지 재시도
        try {
            for (int i = 0; i < 8; i++) {
                Thread.sleep(100);
                String again = stringRedisTemplate.opsForValue().get(key);
                if (again != null) {
                    return objectMapper.readValue(again, new TypeReference<Map<String,Integer>>() {});
                }
            }
        } catch (Exception ignore) {}

        // 4) 최후의 수단: 직접 호출 후 캐시
        try {
            Map<String,Integer> data = toIntMap(testFlaskService.getSpend(memberId));
            stringRedisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(data), TTL);
            return data;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String,Integer> toIntMap(Map<String,Object> raw) {
        Map<String,Integer> m = new HashMap<>();
        for (var e : raw.entrySet()) {
            Object v = e.getValue();
            int iv = (v instanceof Number) ? ((Number) v).intValue()
                    : Integer.parseInt(String.valueOf(v));
            m.put(e.getKey(), iv);
        }
        return m;
    }
}
