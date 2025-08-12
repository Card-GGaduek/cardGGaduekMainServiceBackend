package org.cardGGaduekMainService.codef.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CodefAccountRegisterResponse{

	@JsonProperty("result")
	private Result result;

	@JsonProperty("data")
	private Response data;
}