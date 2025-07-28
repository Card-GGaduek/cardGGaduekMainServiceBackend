package org.cardGGaduekMainService.store.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreVO {

    private Long id;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private Time openTime;
    private Time closeTime;
    private String storeCategory;

}
