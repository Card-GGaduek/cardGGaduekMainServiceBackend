package org.cardGGaduekMainService.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cardGGaduekMainService.card.benefit.dto.CardBenefitDTO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreBenefitDTO {
    private Long id;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    List<CardBenefitDTO> benefits;
}
