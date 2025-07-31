package org.cardGGaduekMainService.place.domain;

import java.util.List;
import lombok.Data;

@Data
public class PlaceResponse {
	private List<PlacesItem> places;
}