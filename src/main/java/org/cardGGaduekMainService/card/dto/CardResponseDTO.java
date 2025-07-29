package org.cardGGaduekMainService.card.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardResponseDTO {
    private Long cardId;
    private String cardImageUrl;
    private String cardProductName;
    private String bankName;
}
