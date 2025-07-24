package org.cardGGaduekMainService.lab.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cardGGaduekMainService.lab.domain.enums.SpendingCategory;

import java.time.LocalDateTime;
import java.time.YearMonth;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpendingAnalysisResultVO {
    private SpendingCategory category;  // 분석 카테고리 (분석 결과 타이틀과 분석 결과 이미지 enum)
    private YearMonth analysisMonth;    // 분석 기준 월
    private LocalDateTime updatedAt;    // 분석 결과 갱신일
}
