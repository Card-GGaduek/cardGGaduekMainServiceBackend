package org.cardGGaduekMainService.product.accommodation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccommodationDTO {
    private Long id;
    private String name;
    private String type;
    private String address;
    private String checkInTime;
    private String checkOutTime;
}
