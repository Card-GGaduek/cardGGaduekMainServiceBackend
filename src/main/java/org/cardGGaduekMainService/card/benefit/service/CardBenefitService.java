package org.cardGGaduekMainService.card.benefit.service;

import org.cardGGaduekMainService.card.benefit.mapper.CardBenefitMapper;
import org.cardGGaduekMainService.card.mapper.CardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.smartcardio.Card;
import java.math.BigDecimal;

@Service
public interface CardBenefitService {

    BigDecimal getCardDiscountAmount(Long userCardId, BigDecimal priceToApply, String benefitCategory);
}
