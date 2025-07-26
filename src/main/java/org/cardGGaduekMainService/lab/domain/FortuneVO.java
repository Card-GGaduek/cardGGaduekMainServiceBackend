package org.cardGGaduekMainService.lab.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cardGGaduekMainService.lab.domain.enums.FortuneLevel;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FortuneVO {
    public FortuneLevel getFortuneLevel() {
        return FortuneLevel.fromIndex(this.fortuneIndex);
    }
    private int fortuneIndex;           // DB에서 직접 받아오는 운세 지수
    private String luckyItem;           // 행운의 아이템
    private String luckyItemImageUrl;   // 행운의 아이템 이미지
    private LocalDateTime createdAt;    // 생성일
    private boolean isTodayPicked;      // 오늘 뽑았는지 여부
}
