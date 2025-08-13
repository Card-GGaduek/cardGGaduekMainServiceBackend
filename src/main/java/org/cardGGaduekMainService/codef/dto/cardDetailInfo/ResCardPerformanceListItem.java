package org.cardGGaduekMainService.codef.dto.cardDetailInfo;

import lombok.Data;

@Data
public class ResCardPerformanceListItem{
	private String commStartDate;
	private String resRequiredPerformanceAmt;
	private String resMeetPerformanceYN;
	private String resCoverageCriteria;
	private String resCurrentUseAmt;
	private String commEndDate;
	private String resStandardUseAmt;
}