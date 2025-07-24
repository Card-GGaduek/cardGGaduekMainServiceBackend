package org.cardGGaduekMainService.card.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardVO {
    private Long id;
    private Long memberId;
    private Long cardProductId;
    private String cardNumber;
    private LocalDate expirationDate;
    private String cvc;
    private LocalDate createdAt;
    private String cardImageUrl;
    private String cardCategory;
    private String customImageUrl;
}
