package org.cardGGaduekMainService.cardRecommend.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.cardGGaduekMainService.cardRecommend.mapper.SpendPredictMapper;
import org.cardGGaduekMainService.cardRecommend.service.TestFlaskService;
import org.cardGGaduekMainService.member.mapper.MemberMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class SpendPredictScheduler {

    private final TestFlaskService testFlaskService;
    private final SpendPredictMapper spendPredictMapper;
    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;
    private final MemberMapper memberMapper;

    private static final ZoneId KST = ZoneId.of("Asia/Seoul");
    private static final DateTimeFormatter YM = DateTimeFormatter.ofPattern("yyyyMM");

    // 매달 1일 00:00 KST
    @Scheduled(cron = "0 0 0 1 * *", zone = "Asia/Seoul")
    //@Scheduled(cron = "0/30 * * * * *", zone = "Asia/Seoul")
    public void spendPredictScheduling() {
        String yearMonth = YearMonth.now(KST).format(YM);
        Duration ttl = ttlToNextMonthKst();
        List<Long> memberIds = memberMapper.findAllMemberIds();

        for (Long memberId : memberIds) {
            try {
                Map<String,Object> jsonData = testFlaskService.getSpend(memberId);
                Map<String,Integer> data = toIntMap(jsonData);
                String json = objectMapper.writeValueAsString(data);

                // 1) DB 저장
                spendPredictMapper.updateSpendPreidct(memberId, yearMonth, json);

                // 2) Redis 프리워밍
                String key = cacheKey(memberId, yearMonth);
                stringRedisTemplate.opsForValue().set(key, json, ttl);
            } catch (Exception e) {
                log.error("SpendPredictScheduler scheduling failed. memberId={}", memberId, e);
            }
        }
    }

    private static String cacheKey(Long memberId, String yearMonth) {
        return "pred:%d:%s".formatted(memberId, yearMonth);
    }

    private static Duration ttlToNextMonthKst() {
        ZonedDateTime now = ZonedDateTime.now(KST);
        ZonedDateTime next = now.withDayOfMonth(1).plusMonths(1).truncatedTo(ChronoUnit.DAYS);
        return Duration.between(now, next);
    }

    @SuppressWarnings("unchecked")
    private static Map<String,Integer> toIntMap(Map<String,Object> rawData) {
        Map<String,Integer> m = new LinkedHashMap<>();
        for (var e : rawData.entrySet()) {
            Object v = e.getValue();
            int iv = (v instanceof Number) ? ((Number) v).intValue() : parseIntSafe(v);
            m.put(e.getKey(), iv);
        }
        return m;
    }

    private static int parseIntSafe(Object v) {
        try {
            return Integer.parseInt(String.valueOf(v));
        }
        catch (Exception ex) {
            return 0;
        }
    }

}
