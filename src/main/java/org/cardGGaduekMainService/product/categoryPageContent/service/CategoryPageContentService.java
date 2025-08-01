package org.cardGGaduekMainService.product.categoryPageContent.service;

import org.cardGGaduekMainService.card.benefit.dto.StoreBenefitDTO;
import org.cardGGaduekMainService.product.categoryPageContent.domain.CategoryPageContentVO;
import org.cardGGaduekMainService.product.categoryPageContent.dto.CategoryPageContentDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryPageContentService {
    List<CategoryPageContentDTO> getBenefitContentForMember(String categoryName, Long memberId);

    StoreBenefitDTO findBestBenefit(List<StoreBenefitDTO> benefits);
}
