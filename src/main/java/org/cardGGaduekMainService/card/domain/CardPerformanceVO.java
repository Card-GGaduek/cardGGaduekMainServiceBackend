package org.cardGGaduekMainService.card.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardPerformanceVO {
    private Long id;
    private Long cardId;
    private Long memberId;
    private Long cardProductId;
    private Date month;
    private BigDecimal actualAmount;
    private BigDecimal goalAmount;
    private String status;
}
