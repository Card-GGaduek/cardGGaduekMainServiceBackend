package org.cardGGaduekMainService.product.categoryPageContent.service;

import org.cardGGaduekMainService.card.benefit.domain.CardBenefitVO;
import org.cardGGaduekMainService.card.benefit.mapper.CardBenefitMapper;
import org.cardGGaduekMainService.card.mapper.CardMapper;
import org.cardGGaduekMainService.product.categoryPageContent.domain.CategoryPageContentVO;
import org.cardGGaduekMainService.product.categoryPageContent.dto.CategoryPageContentDTO;
import org.cardGGaduekMainService.product.categoryPageContent.mapper.CategoryPageContentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CategoryPageContentServiceImpl implements CategoryPageContentService{
    private final CategoryPageContentMapper categoryPageContentMapper;
    private final CardMapper cardMapper;
    private final CardBenefitMapper cardBenefitMapper;

    @Autowired
    public CategoryPageContentServiceImpl(CategoryPageContentMapper categoryPageContentMapper, CardMapper cardMapper, CardBenefitMapper cardBenefitMapper){
        this.categoryPageContentMapper = categoryPageContentMapper;
        this.cardMapper = cardMapper;
        this.cardBenefitMapper = cardBenefitMapper;
    }


    @Override
    public List<CategoryPageContentDTO> getBenefitContentForMember(String categoryName, Long memberId){
        List<CategoryPageContentDTO> allContents = categoryPageContentMapper.findByCategoryName(categoryName);
       List<Long> userCardProductIds = cardMapper.findAllCardProductIdsByMemberId(memberId);
        List<CategoryPageContentDTO> resultList = new ArrayList<>();

        for(CategoryPageContentDTO content : allContents){
            CardBenefitVO bestBenefit = cardBenefitMapper.findBestBenefitForStore(content.getCategoryName(), userCardProductIds);
            if(bestBenefit != null) {
                CategoryPageContentDTO dto = new CategoryPageContentDTO();
                dto.setCategoryName(content.getCategoryName());
                dto.setDescription(content.getDescription());
                dto.setImageUrl(content.getImageUrl());
                dto.setLinkUrl(content.getLinkUrl());
                dto.setId(content.getId());
                dto.setTitle(content.getTitle());

                resultList.add(dto);
            }
        }

       if(userCardProductIds == null || userCardProductIds.isEmpty()){
           return Collections.emptyList();
       }

       return resultList;
    }
}
