package org.cardGGaduekMainService.product.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentReadyDTO {
    private String orderId;
    private String orderName;
    private BigDecimal amount;
    private String customerName;
}
