package org.cardGGaduekMainService.product.accommodation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cardGGaduekMainService.product.rooms.dto.RoomsDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccommodationPageDTO {
    private Long id;
    private String name;
    private String type;
    private String address;
    private String checkInTime;
    private String checkOutTime;
    private List<RoomsDTO> rooms;
}
