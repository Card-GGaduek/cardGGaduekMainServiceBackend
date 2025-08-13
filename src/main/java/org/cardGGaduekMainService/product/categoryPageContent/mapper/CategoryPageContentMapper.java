package org.cardGGaduekMainService.product.categoryPageContent.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cardGGaduekMainService.product.categoryPageContent.domain.CategoryPageContentVO;
import org.cardGGaduekMainService.product.categoryPageContent.dto.BenefitDetailDTO;
import org.cardGGaduekMainService.product.categoryPageContent.dto.CategoryPageContentDTO;

import java.util.List;
import java.util.Map;

@Mapper
public interface CategoryPageContentMapper {

    List<BenefitDetailDTO> findAllApplicableBenefitsForMember(Map<String, Object> params);
}
