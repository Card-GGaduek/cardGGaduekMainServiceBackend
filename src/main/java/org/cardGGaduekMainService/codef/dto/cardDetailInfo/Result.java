package org.cardGGaduekMainService.codef.dto.cardDetailInfo;

import lombok.Data;

@Data
public class Result{
	private String code;
	private String extraMessage;
	private String message;
	private String transactionId;
}