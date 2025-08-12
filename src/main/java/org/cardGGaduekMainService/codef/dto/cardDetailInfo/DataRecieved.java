package org.cardGGaduekMainService.codef.dto.cardDetailInfo;

import lombok.Data;

import java.util.List;

@Data
public class DataRecieved {
	private String resCardType;
	private String resCardName;
	private List<ResCardBenefitListItem> resCardBenefitList;
	private String resLnkUrl;
	private String resCardNo;
	private String resCardCompany;
}