package org.cardGGaduekMainService.card.benefit.service;

import lombok.extern.log4j.Log4j2;
import org.cardGGaduekMainService.card.benefit.domain.CardBenefitVO;
import org.cardGGaduekMainService.card.benefit.dto.StoreBenefitDTO;
import org.cardGGaduekMainService.card.benefit.mapper.CardBenefitMapper;
import org.cardGGaduekMainService.card.mapper.CardMapper;
import org.cardGGaduekMainService.product.categoryPageContent.mapper.CategoryPageContentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

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

        log.info(">>>>> 3. 조회 조건: cardProductId={}, storeCategory={}", cardProductId, benefitCategory);
        if(cardProductId == null){
            return BigDecimal.ZERO;
        }

        List<StoreBenefitDTO> benefits = cardBenefitMapper.findBenefits(cardProductId, benefitCategory);
        log.info(">>>>> 4. 조회된 혜택 개수: {}", benefits.size());
        log.info(">>>> DB 조회 파라미터 확인: cardProductId={}, benefitCategory='{}'", cardProductId, benefitCategory);
        BigDecimal maxDiscount = BigDecimal.ZERO;
        for(StoreBenefitDTO benefit : benefits){
           BigDecimal currentDiscount = BigDecimal.ZERO;
           if("PERCENT".equalsIgnoreCase(benefit.getValueType()) && benefit.getRateValue() != null){
               BigDecimal rate = benefit.getRateValue().divide(new BigDecimal("100"));
               currentDiscount = priceToApply.multiply(rate);
           } else if("AMOUNT".equalsIgnoreCase(benefit.getValueType()) && benefit.getAmountValue() != null){
               currentDiscount = new BigDecimal(benefit.getAmountValue());
           }

           if(currentDiscount.compareTo(maxDiscount) > 0){
               maxDiscount = currentDiscount;
           }
           log.info(">>>> DB 조회 파라미터 확인: cardProductId={}, benefitCategory='{}', discount_rate = {}", cardProductId, benefitCategory, currentDiscount);
       }

        return maxDiscount;
    }
}
