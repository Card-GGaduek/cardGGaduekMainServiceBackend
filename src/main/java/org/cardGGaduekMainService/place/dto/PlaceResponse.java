package org.cardGGaduekMainService.place.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceResponse {
	private List<PlaceDTO> places;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PlaceDTO {
		private String name;
		private String category;
		private LocationDTO locationDTO;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class LocationDTO {
		private Double latitude;
		private Double longitude;
	}
}