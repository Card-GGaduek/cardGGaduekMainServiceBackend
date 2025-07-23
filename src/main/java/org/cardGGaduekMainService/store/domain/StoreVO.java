package org.cardGGaduekMainService.store.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    private Date open_time;
    private Date close_time;
    private enum store_category {
        CONVENIENCE,RESTAURANT,CAFE,GAS_STATION
    }

}
