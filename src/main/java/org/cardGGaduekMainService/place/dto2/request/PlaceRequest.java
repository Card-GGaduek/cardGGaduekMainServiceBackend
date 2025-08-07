package org.cardGGaduekMainService.place.dto2.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceRequest{
	@JsonProperty("locationRestriction")
	private LocationRestriction locationRestriction;
	private String textQuery;
	private final String languageCode = "ko";
}