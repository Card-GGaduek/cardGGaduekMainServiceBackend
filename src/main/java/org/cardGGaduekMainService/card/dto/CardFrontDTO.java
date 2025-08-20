package org.cardGGaduekMainService.card.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardFrontDTO {
    private Long cardId;
    private String cardImageUrl;     // 카드 이미지
    private String cardProductName;  // 카드 이름
    private String bankName;         // 발급사
    private String memberName;       // 사용자 이름
    private int isValid;                // 0: 사용 불가능, 1: 사용 가능 상태를 나타냄
}