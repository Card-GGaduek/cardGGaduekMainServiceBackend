package org.cardGGaduekMainService.card.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardVO {
    private Long id;
    private Long customerId;
    private Long accountId;
    private Long cardProductId;
    private String cardNumber;
    private String cvc;
    private Date cardIssuedDate;
    private Date cardExpiredDate;
    private String cardPassword;
}
