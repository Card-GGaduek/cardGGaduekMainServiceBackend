package org.cardGGaduekMainService.product.accommodation.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccommodationVO {
    private Long id;
    private String name;
    private String type;
    private String address;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
}