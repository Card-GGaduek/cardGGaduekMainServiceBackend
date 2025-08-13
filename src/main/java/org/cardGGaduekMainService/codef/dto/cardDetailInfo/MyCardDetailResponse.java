package org.cardGGaduekMainService.codef.dto.cardDetailInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MyCardDetailResponse {
	private Result result;

	@JsonProperty("data")
	private DataRecieved data;
	private String connectedId;
}