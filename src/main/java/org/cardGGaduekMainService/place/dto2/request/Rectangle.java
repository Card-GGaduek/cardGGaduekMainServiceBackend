package org.cardGGaduekMainService.place.dto2.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rectangle{
	@JsonProperty("high")
	private High high;

	@JsonProperty("low")
	private Low low;
}