package org.cardGGaduekMainService.totalbenefit.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cardGGaduekMainService.totalbenefit.dto.TotalBenefitDTO;

import java.util.List;

@Mapper
public interface TotalBenefitMapper {
    List<TotalBenefitDTO.CategoryBenefit> findCategoryBenefitSummary(
            @Param("memberId") Long memberId,
            @Param("yearMonth") String yearMonth
    );
}
