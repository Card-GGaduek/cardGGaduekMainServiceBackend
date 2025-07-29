package org.cardGGaduekMainService.card.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardImageDTO {
    private String imageUrl; // null이면 기본 이미지로 변경

    // 프론트엔드에서 이미지 출력 시 로직 : const finalImage = card.customImageUrl ?? card.cardImageUrl;
}
