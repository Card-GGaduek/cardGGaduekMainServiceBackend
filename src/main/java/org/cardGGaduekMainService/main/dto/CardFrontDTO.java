package org.cardGGaduekMainService.main.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardFrontDTO {
    private Long cardId;
    private String cardNumber;
    private String cardholderName;
    private String cardCompany;
    private String cardImageUrl;  // ✅ 추가
}

