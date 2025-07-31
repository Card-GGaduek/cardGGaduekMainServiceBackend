package org.cardGGaduekMainService.totalbenefit.service;

import org.cardGGaduekMainService.totalbenefit.dto.TotalBenefitDTO;

public interface TotalBenefitService {
    TotalBenefitDTO getTotalBenefitSummary(Long memberId, String yearMonth);
}
