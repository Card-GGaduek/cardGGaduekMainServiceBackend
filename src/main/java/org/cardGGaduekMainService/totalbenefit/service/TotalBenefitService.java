package org.cardGGaduekMainService.totalbenefit.service;

import org.cardGGaduekMainService.totalbenefit.dto.TotalBenefitResponseDTO;

public interface TotalBenefitService {
    TotalBenefitResponseDTO getTotalBenefit(Long memberId, String yearMonth);
}
