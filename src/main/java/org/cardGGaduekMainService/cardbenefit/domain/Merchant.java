package org.cardGGaduekMainService.cardbenefit.domain;

import lombok.Data;

import java.util.List;

@Data
public class Merchant {
    private Long id;
    private String name;
    private String address;
    private String category;
    private double latitude;
    private double longitude;
    private List<CardBenefit> benefits;
    private List<Merchant> merchants;
    private Long benefitId;
}
