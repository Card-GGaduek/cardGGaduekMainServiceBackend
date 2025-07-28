package org.cardGGaduekMainService.card.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardVO {
    private Long id;
    private String cardNumber;
    private LocalDateTime expirationDate;
    private String cvc;
    private String cardPassword;
    private LocalDateTime createdAt;
    private String customImageUrl;
    private Long cardProductId;
    private Long memberId;
    private boolean isValid;
}