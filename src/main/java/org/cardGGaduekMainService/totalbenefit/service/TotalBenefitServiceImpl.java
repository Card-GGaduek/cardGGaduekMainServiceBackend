package org.cardGGaduekMainService.totalbenefit.service;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.totalbenefit.dto.TotalBenefitDTO;
import org.cardGGaduekMainService.totalbenefit.dto.TotalBenefitDTO.CategoryBenefit;
import org.cardGGaduekMainService.totalbenefit.mapper.TotalBenefitMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TotalBenefitServiceImpl implements TotalBenefitService {

    private final TotalBenefitMapper totalBenefitMapper;

    @Override
    public TotalBenefitDTO getTotalBenefitSummary(Long memberId, String yearMonth) {
        List<TotalBenefitDTO.CategoryBenefit> categoryBenefits =
                totalBenefitMapper.findCategoryBenefitSummary(memberId, yearMonth);

        int total = categoryBenefits.stream()
                .mapToInt(TotalBenefitDTO.CategoryBenefit::getAmount)
                .sum();

        TotalBenefitDTO dto = new TotalBenefitDTO();
        dto.setTotalBenefit(total);
        dto.setCategoryBenefits(categoryBenefits);

        return dto;
    }
}

