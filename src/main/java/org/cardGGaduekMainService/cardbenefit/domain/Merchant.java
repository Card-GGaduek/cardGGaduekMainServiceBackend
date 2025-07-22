package org.cardGGaduekMainService.cardbenefit.domain;

import lombok.Data;

@Data
public class Merchant {
    private Long merchantId;
    private String name;
    private String category;
    private double latitude;
    private double longitude;
}
