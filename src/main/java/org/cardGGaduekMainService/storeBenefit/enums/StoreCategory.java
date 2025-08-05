package org.cardGGaduekMainService.storeBenefit.enums;

public enum StoreCategory {
    CONVENIENCE_STORE("convenience_store"),
    GAS_STATION("gas_station"),
    MOVIE_THEATER("movie_theater"),
    COFFEE_SHOP("coffee_shop"),
    RESTAURANT("restaurant"),
    HOTEL("hotel"),
    THEME_PARK("theme_park"),
    UNCATEGORIZED("uncategorized");


    private String value;

    StoreCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
