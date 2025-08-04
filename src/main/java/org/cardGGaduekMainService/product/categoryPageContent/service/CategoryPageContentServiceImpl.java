package org.cardGGaduekMainService.product.categoryPageContent.service;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.cardGGaduekMainService.card.benefit.domain.CardBenefitVO;
import org.cardGGaduekMainService.card.benefit.dto.CardBenefitDTO;
import org.cardGGaduekMainService.card.benefit.dto.StoreBenefitDTO;
import org.cardGGaduekMainService.card.benefit.mapper.CardBenefitMapper;
import org.cardGGaduekMainService.card.mapper.CardMapper;
import org.cardGGaduekMainService.cardProduct.mapper.CardProductMapper;
import org.cardGGaduekMainService.product.categoryPageContent.domain.CategoryPageContentVO;
import org.cardGGaduekMainService.product.categoryPageContent.dto.CategoryPageContentDTO;
import org.cardGGaduekMainService.product.categoryPageContent.mapper.CategoryPageContentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Log4j2
@Service
public class CategoryPageContentServiceImpl implements CategoryPageContentService{
    private final CategoryPageContentMapper categoryPageContentMapper;
    private final CardMapper cardMapper;
    private final CardBenefitMapper cardBenefitMapper;
    private final CardProductMapper cardProductMapper;

    @Autowired
    public CategoryPageContentServiceImpl(CategoryPageContentMapper categoryPageContentMapper, CardMapper cardMapper, CardBenefitMapper cardBenefitMapper, CardProductMapper cardProductMapper){
        this.categoryPageContentMapper = categoryPageContentMapper;
        this.cardMapper = cardMapper;
        this.cardBenefitMapper = cardBenefitMapper;
        this.cardProductMapper = cardProductMapper;
    }



    @Override
    public List<CategoryPageContentDTO> getBenefitContentForMember(String categoryName, Long memberId){
        log.info(">>>>> 1. 서비스 시작: categoryName='{}', memberId={}", categoryName, memberId);
        CategoryPageContentVO categoryPageContentVO = new CategoryPageContentVO();

        List<Long> userCardProductIds = cardMapper.findAllCardProductIdsByMemberId(memberId);
        log.info(">>>>> 2. 조회된 사용자 카드 상품 ID 목록: {}", userCardProductIds);
        if(userCardProductIds == null || userCardProductIds.isEmpty()){
            log.warn(">>>>> 사용자가 카드를 보유하고 있지 않아 빈 목록을 반환합니다.");
            return Collections.emptyList();
        }
        List<CategoryPageContentDTO> allContents = categoryPageContentMapper.findByCategoryName(categoryName);
        List<CategoryPageContentDTO> resultList = new ArrayList<>();

        for(CategoryPageContentDTO content: allContents){
            List<StoreBenefitDTO> benefitsForStore = cardBenefitMapper.findBenefitsByStoreName(content.getTitle(), userCardProductIds);

            StoreBenefitDTO bestBenefit = findBestBenefit(benefitsForStore);

            if(bestBenefit != null){
                String cardName = cardProductMapper.findNameById(bestBenefit.getCardProductId());
                content.setCardName(cardName);
                if("PERCENT".equalsIgnoreCase(bestBenefit.getValueType())){
                    content.setDiscountRate(bestBenefit.getRateValue());
                } else if("AMOUNT".equalsIgnoreCase(bestBenefit.getValueType())){
                    content.setDiscountRate(BigDecimal.valueOf(bestBenefit.getAmountValue()));
                }
                resultList.add(content);
            }
        }
//        for(CategoryPageContentDTO content : allContents){
//            log.info(">>>>> 3. 혜택 조회 시작 (Mapper 호출 직전)");
//            CardBenefitVO bestBenefit = cardBenefitMapper.findBestBenefitForStore(content.getCategoryName(), userCardProductIds);

//            if(bestBenefit != null) {
//                CategoryPageContentDTO dto = new CategoryPageContentDTO();
//                dto.setCategoryName(content.getCategoryName());
//                dto.setDescription(content.getDescription());
//                dto.setImageUrl(content.getImageUrl());
//                dto.setLinkUrl(content.getLinkUrl());
//                dto.setId(content.getId());
//                dto.setTitle(content.getTitle());
//                if("RATE".equalsIgnoreCase(bestBenefit.getValueType())){
//                    dto.setDiscountRate(bestBenefit.getRateValue());
//                }
//                resultList.add(dto);
//            }
        return resultList;
        }

    public StoreBenefitDTO findBestBenefit(List<StoreBenefitDTO> benefits){
        if(benefits == null || benefits.isEmpty()) {
            return null;
        }
        return benefits.get(0);
    }
}