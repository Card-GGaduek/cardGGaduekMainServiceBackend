package org.cardGGaduekMainService.place.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleApiResponse {
    private List<Place> places = new ArrayList<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Place{
        private DisplayName displayName;
        private List<String> types;
        private Location location;
        private String formattedAddress;
        private String primaryType;
    }

    @Data
    @NoArgsConstructor
    public static class DisplayName{
        private String text;
        private String languageCode;
    }

    @Data
    @NoArgsConstructor
    public static class Location{
        private Double latitude;
        private Double longitude;
    }
}
