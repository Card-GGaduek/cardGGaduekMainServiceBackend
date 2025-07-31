package org.cardGGaduekMainService.card.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh-mm" )
    private LocalDateTime createdAt;
    private String customImageUrl;
    private Long cardProductId;
    private Long memberId;
    private boolean isValid;
}

