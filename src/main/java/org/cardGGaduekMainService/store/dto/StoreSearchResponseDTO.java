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

    public static StoreSearchResponseDTO from(StoreVO store) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        String openTimeStr = store.getOpenTime()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalTime()
                .format(formatter);

        String closeTimeStr = store.getCloseTime()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalTime()
                .format(formatter);

        return StoreSearchResponseDTO.builder()
                .id(store.getId())
                .name(store.getName())
                .address(store.getAddress())
                .latitude(store.getLatitude())
                .longitude(store.getLongitude())
                .openTime(openTimeStr)
                .closeTime(closeTimeStr)
                .build();
    }


    }

