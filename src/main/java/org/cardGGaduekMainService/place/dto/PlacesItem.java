package org.cardGGaduekMainService.place.dto;

import lombok.Data;
import org.cardGGaduekMainService.place.domain.DisplayName;
import org.cardGGaduekMainService.place.domain.Location;

@Data
public class PlacesItem{
	private String formattedAddress;
	private DisplayName displayName;
	private String primaryType;
	private Location location;
}