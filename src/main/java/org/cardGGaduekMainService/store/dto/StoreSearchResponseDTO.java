package org.cardGGaduekMainService.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cardGGaduekMainService.store.domain.StoreVO;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreSearchResponseDTO {
    private Long id;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private String openTime;
    private String closeTime;
    private String storeCategory;


    // 오픈시간 - 마감시간 time 테이블 포맷 변경 (ex : 08:00 , 23:00)
    public static StoreSearchResponseDTO from(StoreVO store) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        String openTimeStr = (store.getOpenTime() != null)
                ? store.getOpenTime().toLocalTime().format(formatter)
                : null;

        String closeTimeStr = (store.getCloseTime() != null)
                ? store.getCloseTime().toLocalTime().format(formatter)
                : null;

        return StoreSearchResponseDTO.builder()
                .id(store.getId())
                .name(store.getName())
                .address(store.getAddress())
                .latitude(store.getLatitude())
                .longitude(store.getLongitude())
                .openTime(openTimeStr)
                .closeTime(closeTimeStr)
                .storeCategory(store.getStoreCategory())
                .build();
    }


    }

