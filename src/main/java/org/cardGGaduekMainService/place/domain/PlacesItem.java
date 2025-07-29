package org.cardGGaduekMainService.place.domain;

import lombok.Data;

@Data
public class PlacesItem{
	private String formattedAddress;
	private DisplayName displayName;
	private String primaryType;
	private Location location;
}