package org.cardGGaduekMainService.place.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cardGGaduekMainService.place.domain.DisplayName;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoogleApiResponse {
    private List<Place> place;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Place{
        private DisplayName displayName;
        private List<String> types;
        private Location location;
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
