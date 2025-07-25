package org.cardGGaduekMainService.lab.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MissionProgressVO {
    private Long missionId;         // 미션ID
    private String missionTitle;    // 미션명
    private String description;     // 미션 설명
    private String category;        // 미션 카테고리 (FOOD, SHOPPING, MEDICAL, CULTURE, TRANSPORT)
    private String reward;          // 미션 보상
    private LocalDateTime startAt;  // 미션 시작일
    private LocalDateTime endAt;    // 미션 종료일

    private int progressValue;      // 현재 진행값
    private int goalValue;          // 목표값
    private String progressStatus;  // 진행 상태(진행중, 미션성공)
}
