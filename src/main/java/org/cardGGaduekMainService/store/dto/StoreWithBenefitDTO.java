package org.cardGGaduekMainService.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cardGGaduekMainService.card.benefit.dto.CardBenefitDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreWithBenefitDTO {
    private Long storeId;
    private String storeName;
    private String address;
    private Double latitude;
    private Double longitude;
    private String openTime;
    private String closeTime;
    private Integer storeCategoryId;
    private List<CardBenefitDTO> benefits;
}
