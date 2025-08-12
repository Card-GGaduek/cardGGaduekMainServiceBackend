package org.cardGGaduekMainService.codef.dto.cardInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import reactor.util.annotation.Nullable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MyCardResponse {
	@JsonProperty("resCardType")
	private String resCardType;

	@JsonProperty("resValidPeriod")
	private String resValidPeriod;

	@JsonProperty("resState")
	private String resState;

	@Nullable
	private String organizationCode;

	@JsonProperty("resCardName")
	private String resCardName;

	@JsonProperty("resTrafficYN")
	private String resTrafficYN;

	@JsonProperty("resIssueDate")
	private String resIssueDate;

	@JsonProperty("resSleepYN")
	private String resSleepYN;

	@JsonProperty("resCardNo")
	private String cardNumber;

	@JsonProperty("resImageLink")
	private String resImageLink;

}