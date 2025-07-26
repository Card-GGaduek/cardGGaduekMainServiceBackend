package org.cardGGaduekMainService.lab.dto;

import lombok.Data;
import org.cardGGaduekMainService.lab.domain.FortuneVO;
import org.cardGGaduekMainService.lab.domain.enums.FortuneLevel;

import java.time.LocalDateTime;

@Data
public class FortuneResponseDTO {
    private int fortuneIndex;
    private String message;
    private String luckyItem;
    private String luckyItemImageUrl;
    private LocalDateTime createdAt;
    private boolean isTodayPicked;

    public static FortuneResponseDTO from(FortuneVO vo) {
        FortuneLevel level = vo.getFortuneLevel();

        FortuneResponseDTO dto = new FortuneResponseDTO();
        dto.setFortuneIndex(level.getIndex());
        dto.setMessage(level.getMessage());
        dto.setLuckyItem(vo.getLuckyItem());
        dto.setLuckyItemImageUrl("/api/lab/fortune/image/" + vo.getLuckyItemImageUrl());
        dto.setCreatedAt(vo.getCreatedAt());
        dto.setTodayPicked(vo.isTodayPicked());
        return dto;
    }
}
