package org.cardGGaduekMainService.place.dto;

import lombok.Data;

@Data
public class Response{
	private LocationBias locationBias;
	private String textQuery;
	private String languageCode;
	private String category;
}