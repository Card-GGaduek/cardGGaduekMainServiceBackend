package org.cardGGaduekMainService.card.domain;

import java.math.BigDecimal;
import java.util.Date;

public class CardPerformanceVO {
    private Long id;
    private Long cardId;
    private Long memberId;
    private Long cardProductId;
    private Date month;
    private BigDecimal actualAmount;
    private BigDecimal goalAmount;
    private String status;

    // Getter, Setter, toString() 등 Lombok 사용 시 @Data 가능
}
