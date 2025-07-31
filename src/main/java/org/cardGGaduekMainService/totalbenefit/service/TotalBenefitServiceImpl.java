package org.cardGGaduekMainService.totalbenefit.service;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.exception.CustomException;
import org.cardGGaduekMainService.exception.ErrorCode;
import org.cardGGaduekMainService.totalbenefit.dto.CategoryBenefitDTO;
import org.cardGGaduekMainService.totalbenefit.dto.TotalBenefitResponseDTO;
import org.cardGGaduekMainService.totalbenefit.mapper.TotalBenefitMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TotalBenefitServiceImpl implements TotalBenefitService {

    private final TotalBenefitMapper totalBenefitMapper;

    @Override
    public TotalBenefitResponseDTO getTotalBenefit(Long memberId, String yearMonth) {
        List<CategoryBenefitDTO> benefits = totalBenefitMapper.getCategoryBenefits(memberId, yearMonth);

        if (benefits == null || benefits.isEmpty()) {
            throw new CustomException(ErrorCode.TOTAL_BENEFIT_NOT_FOUND);
        }

        long total = benefits.stream()
                .mapToLong(CategoryBenefitDTO::getAmount)
                .sum();

        return new TotalBenefitResponseDTO(total, benefits);
    }
}