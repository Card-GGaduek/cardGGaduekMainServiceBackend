package org.cardGGaduekMainService.product.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceResponseDTO {
    private BigDecimal originalPrice;
    private BigDecimal couponDiscountAmount;
    private BigDecimal cardDiscountAmount;
    private BigDecimal finalPrice;
}
