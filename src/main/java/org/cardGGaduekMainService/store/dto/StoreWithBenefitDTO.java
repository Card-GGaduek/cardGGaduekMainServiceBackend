package org.cardGGaduekMainService.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cardGGaduekMainService.card.benefit.dto.CardBenefitDTO;
import org.cardGGaduekMainService.store.domain.StoreVO;

import java.time.format.DateTimeFormatter;
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
    private String storeCategory;
    private List<CardBenefitDTO> benefits;

    // 오픈시간 - 마감시간 time 테이블 포맷 변경 (ex : 08:00 , 23:00)
    public static StoreWithBenefitDTO from(StoreVO store) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        String openTimeStr = (store.getOpenTime() != null)
                ? store.getOpenTime().toLocalTime().format(formatter)
                : null;

        String closeTimeStr = (store.getCloseTime() != null)
                ? store.getCloseTime().toLocalTime().format(formatter)
                : null;

        return StoreWithBenefitDTO.builder()
                .storeId(store.getId())
                .storeName(store.getName())
                .address(store.getAddress())
                .latitude(store.getLatitude())
                .longitude(store.getLongitude())
                .openTime(openTimeStr)
                .closeTime(closeTimeStr)
                .storeCategory(store.getStoreCategory())
                .build();
    }
}
