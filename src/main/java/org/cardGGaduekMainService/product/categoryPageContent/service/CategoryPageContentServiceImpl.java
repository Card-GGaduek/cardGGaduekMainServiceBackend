package org.cardGGaduekMainService.product.categoryPageContent.service;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.cardGGaduekMainService.card.benefit.domain.CardBenefitVO;
import org.cardGGaduekMainService.card.benefit.dto.StoreBenefitDTO;
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
@Log4j2
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
        log.info(">>>>> 1. 서비스 시작: categoryName='{}', memberId={}", categoryName, memberId);


        List<Long> userCardProductIds = cardMapper.findAllCardProductIdsByMemberId(memberId);
        log.info(">>>>> 2. 조회된 사용자 카드 상품 ID 목록: {}", userCardProductIds);
        if(userCardProductIds == null || userCardProductIds.isEmpty()){
            log.warn(">>>>> 사용자가 카드를 보유하고 있지 않아 빈 목록을 반환합니다.");
            return Collections.emptyList();
        }

        List<StoreBenefitDTO> allCategoryBenefits = cardBenefitMapper.findAllBenefitsForCategory(categoryName, userCardProductIds);
        log.info(">>>>> 3. DB에서 조회된 혜택 개수: {}", allCategoryBenefits.size());
        StoreBenefitDTO bestCategoryBenefit = findBestBenefit(allCategoryBenefits);
        log.info(">>>>> 4. 찾은 최고 혜택: {}", bestCategoryBenefit);
        List<CategoryPageContentDTO> allContents = categoryPageContentMapper.findByCategoryName(categoryName);
        log.info(">>>>> 5. 조회된 콘텐츠 개수: {}", allContents.size());
        List<CategoryPageContentDTO> resultList = new ArrayList<>();
        for(CategoryPageContentDTO content: allContents){
            if("PERCENT".equalsIgnoreCase(bestCategoryBenefit.getValueType())){
                content.setDiscountRate(bestCategoryBenefit.getRateValue());
            }
            resultList.add(content);
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
        }
       return resultList;
    }

    public StoreBenefitDTO findBestBenefit(List<StoreBenefitDTO> benefits){
        return benefits.stream().findFirst().orElse(null);
    }
}
