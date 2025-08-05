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
    private Long memberId;
    private Long cardProductId;
    private String cardNumber;
    private String cvc;
    private String cardPassword;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh-mm" )
    private LocalDateTime expirationDate;
    private LocalDateTime createdAt;
    private String customImageUrl;
    private int isValid; // 0: 사용 불가능, 1: 사용 가능 상태를 나타냄
}

