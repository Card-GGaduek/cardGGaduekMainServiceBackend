package org.cardGGaduekMainService.cardbenefit.domain;

import lombok.Data;

@Data
public class Payment {
    private Long paymentId;
    private Long merchantId;
    private int amount;
    private String status;
}
