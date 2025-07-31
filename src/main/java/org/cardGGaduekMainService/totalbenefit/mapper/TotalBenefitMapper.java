package org.cardGGaduekMainService.totalbenefit.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cardGGaduekMainService.totalbenefit.dto.CategoryBenefitDTO;

import java.util.List;

@Mapper
public interface TotalBenefitMapper {
    List<CategoryBenefitDTO> getCategoryBenefits(@Param("memberId") Long memberId, @Param("yearMonth") String yearMonth);
}
