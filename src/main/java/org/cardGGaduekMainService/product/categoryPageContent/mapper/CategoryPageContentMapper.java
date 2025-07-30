package org.cardGGaduekMainService.product.categoryPageContent.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cardGGaduekMainService.product.categoryPageContent.domain.CategoryPageContentVO;
import org.cardGGaduekMainService.product.categoryPageContent.dto.CategoryPageContentDTO;

import java.util.List;

@Mapper
public interface CategoryPageContentMapper {

    List<CategoryPageContentDTO> findByCategoryName(String categoryName);
    List<CategoryPageContentDTO> findBenefitContentForMember(
            @Param("categoryName") String categoryName,
            @Param("userCardProductIds") List<Long> userCardProductIds
    );
}
