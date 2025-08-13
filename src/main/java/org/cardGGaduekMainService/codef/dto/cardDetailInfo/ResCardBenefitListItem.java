package org.cardGGaduekMainService.codef.dto.cardDetailInfo;

import java.util.List;
import lombok.Data;

@Data
public class ResCardBenefitListItem{
	private String resCardBenefitName;
	private String resBusinessTypes;
	private List<ResCardPerformanceListItem> resCardPerformanceList;
	private String resType;
	private List<ResDetailListItem> resDetailList;
}