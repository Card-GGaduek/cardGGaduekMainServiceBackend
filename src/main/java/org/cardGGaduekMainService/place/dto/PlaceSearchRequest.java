package org.cardGGaduekMainService.place.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceSearchRequest {
    private String textQuery;
    private String languageCode;
    private LocationRestriction locationRestriction;
    private String category;

}
