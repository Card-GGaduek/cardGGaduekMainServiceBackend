package org.cardGGaduekMainService.codef.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result{

	@JsonProperty("code")
	private String code;

	@JsonProperty("extraMessage")
	private String extraMessage;

	@JsonProperty("message")
	private String message;

	@JsonProperty("transactionId")
	private String transactionId;
}