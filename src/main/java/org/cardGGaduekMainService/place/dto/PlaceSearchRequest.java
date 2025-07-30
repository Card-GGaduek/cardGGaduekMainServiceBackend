package org.cardGGaduekMainService.place.dto;

import lombok.Data;

@Data
public class PlaceSearchRequest {
    private String name;
    private Double minLatitude;
    private Double minLongitude;
    private Double maxLatitude;
}
