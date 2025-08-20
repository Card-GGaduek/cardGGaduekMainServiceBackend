package org.cardGGaduekMainService.cardRecommend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.cardRecommend.mapper.SpendPredictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SpendPredictionService {

    private final TestFlaskService testFlaskService;
    private final ObjectMapper objectMapper;
    private final SpendPredictMapper spendPredictMapper;

    // Redis가 없으면 null로 두고 동작 (선택 주입)
    @Autowired(required = false)
    private StringRedisTemplate stringRedisTemplate;

    private static final ZoneId KST = ZoneId.of("Asia/Seoul");
    private static final DateTimeFormatter YM = DateTimeFormatter.ofPattern("yyyyMM");

    public Map<String,Integer> predict(Long memberId) {
        String yearMonth = YearMonth.now(KST).format(YM);
        String key  = "pred:%d:%s".formatted(memberId, yearMonth);

        // 1) Redis
        if (stringRedisTemplate != null) {
            try {
                String cached = stringRedisTemplate.opsForValue().get(key);
                if (StringUtils.hasText(cached)) {
                    return objectMapper.readValue(cached, new TypeReference<Map<String,Integer>>() {});
                }
            } catch (Exception ignore) {}
        }

        // 2) DB
        try {
            String fromDb = spendPredictMapper.findSpendPredict(memberId, yearMonth);
            if (StringUtils.hasText(fromDb)) {
                if (stringRedisTemplate != null) {
                    try { stringRedisTemplate.opsForValue().set(key, fromDb, ttlToNextMonthKst()); } catch (Exception ignore) {}
                }
                return objectMapper.readValue(fromDb, new TypeReference<Map<String,Integer>>() {});
            }
        } catch (Exception ignore) {}

        // 3) Flask (최후)
        try {
            Map<String,Integer> data = toIntMap(testFlaskService.getSpend(memberId));
            String json = objectMapper.writeValueAsString(data);

            // DB upsert
            try { spendPredictMapper.updateSpendPreidct(memberId, yearMonth, json); } catch (Exception ignore) {}
            // Redis 백필
            if (stringRedisTemplate != null) {
                try { stringRedisTemplate.opsForValue().set(key, json, ttlToNextMonthKst()); } catch (Exception ignore) {}
            }
            return data;
        } catch (Exception e) {
            throw new RuntimeException("Spend prediction failed (Redis miss, DB miss, Flask error). memberId=" + memberId, e);
        }
    }

    private static Duration ttlToNextMonthKst() {
        ZonedDateTime now = ZonedDateTime.now(KST);
        ZonedDateTime next = now.withDayOfMonth(1).plusMonths(1).truncatedTo(ChronoUnit.DAYS);
        return Duration.between(now, next);
    }

    private Map<String,Integer> toIntMap(Map<String,Object> raw) {
        Map<String,Integer> m = new HashMap<>();
        for (var e : raw.entrySet()) {
            Object v = e.getValue();
            int iv;
            if (v instanceof Number n) iv = n.intValue();
            else {
                try { iv = Integer.parseInt(String.valueOf(v)); }
                catch (Exception ex) { iv = 0; }
            }
            m.put(e.getKey(), iv);
        }
        return m;
    }
}
