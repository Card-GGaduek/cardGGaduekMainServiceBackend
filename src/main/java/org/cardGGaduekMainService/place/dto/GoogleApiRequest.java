package org.cardGGaduekMainService.place.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.awt.*;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class GoogleApiRequest {
    private String textQuery;
    private LocationRestriction locationRestriction;



    @Data
    @AllArgsConstructor
    public static class LocationRestriction {
        private Rectangle rectangle;
    }

    @Data
    @AllArgsConstructor
    public static class Rectangle {
        private LatLng low;
        private LatLng high;
    }

    @Data
    @AllArgsConstructor
    public static class LatLng {
        private double latitude;
        private double longitude;


    }
}
