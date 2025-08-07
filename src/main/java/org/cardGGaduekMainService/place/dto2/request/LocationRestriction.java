package org.cardGGaduekMainService.place.dto2.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationRestriction {
	@JsonProperty("rectangle")
	private Rectangle rectangle;
}