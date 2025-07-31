package org.cardGGaduekMainService.product.rooms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomsDTO {
    private Long id;
    private String name;
    private int pricePerNight;
    private int maxCapacity;
}
