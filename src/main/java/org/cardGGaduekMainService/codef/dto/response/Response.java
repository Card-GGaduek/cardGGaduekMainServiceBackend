package org.cardGGaduekMainService.codef.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Response {

	@JsonProperty("successList")
	private List<SuccessListItem> successList;

	@JsonProperty("errorList")
	private List<Object> errorList;

	@JsonProperty("connectedId")
	private String connectedId;
}