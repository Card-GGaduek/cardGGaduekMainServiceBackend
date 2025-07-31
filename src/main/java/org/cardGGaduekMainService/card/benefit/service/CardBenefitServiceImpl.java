package org.cardGGaduekMainService.card.benefit.service;

import lombok.extern.log4j.Log4j2;
import org.cardGGaduekMainService.card.benefit.domain.CardBenefitVO;
import org.cardGGaduekMainService.card.benefit.mapper.CardBenefitMapper;
import org.cardGGaduekMainService.card.mapper.CardMapper;
import org.cardGGaduekMainService.product.categoryPageContent.mapper.CategoryPageContentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Log4j2
@Service
public class CardBenefitServiceImpl implements CardBenefitService{
    private final CardMapper cardMapper;
    private final CardBenefitMapper cardBenefitMapper;
    private final CategoryPageContentMapper categoryPageContentMapper;

    @Autowired
    public CardBenefitServiceImpl(CardMapper cardMapper,
                                  CardBenefitMapper cardBenefitMapper, CategoryPageContentMapper categoryPageContentMapper) {
        this.cardMapper = cardMapper;
        this.cardBenefitMapper = cardBenefitMapper;
        this.categoryPageContentMapper = categoryPageContentMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getCardDiscountAmount(Long userCardId, BigDecimal priceToApply, String benefitCategory){
        if(userCardId == null || benefitCategory == null || priceToApply == null || priceToApply.compareTo(BigDecimal.ZERO) <= 0){
            return BigDecimal.ZERO;
        }


        Long cardProductId = cardMapper.findCardProductIdByCardId(userCardId);
        if(cardProductId == null){
            return BigDecimal.ZERO;
        }

        log.info(">>>> DB 조회 파라미터 확인: cardProductId={}, benefitCategory='{}'", cardProductId, benefitCategory);
        CardBenefitVO benefit = cardBenefitMapper.findBenefitByProductAndCategory(cardProductId, benefitCategory);
            if(benefit == null) {
                return BigDecimal.ZERO;
            }
        log.info(">>>> DB 조회 파라미터 확인: cardProductId={}, benefitCategory='{}', discount_rate = {}", cardProductId, benefitCategory, benefit.getDiscountRate());
        return benefit.getDiscountRate();
    }
}
