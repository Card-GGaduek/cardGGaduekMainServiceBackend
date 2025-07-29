package org.cardGGaduekMainService.product.rooms.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomsVO {
    private Long id;
    private Long accommodationId;
    private String name;
    private BigDecimal pricePerNight;
    private int maxCapacity;
}
