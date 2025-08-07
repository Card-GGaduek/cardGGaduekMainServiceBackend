package org.cardGGaduekMainService.place.dto2.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Place {
	private List<String> types;
	private String formattedAddress;
	@JsonProperty("displayName")
	private DisplayName displayName;
	private String primaryType;
	@JsonProperty("location")
	private Location location;
}