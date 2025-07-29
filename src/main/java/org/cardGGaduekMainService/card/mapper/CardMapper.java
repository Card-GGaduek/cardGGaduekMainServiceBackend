package org.cardGGaduekMainService.card.mapper;

import org.cardGGaduekMainService.card.dto.CardFrontDTO;
import org.cardGGaduekMainService.card.dto.CardBackDTO;
import org.cardGGaduekMainService.card.dto.CardBenefitInfoDTO;

import java.util.List;

public interface CardMapper {
    List<CardFrontDTO> getCardFrontInfo(Long memberId);

    CardBackDTO getCardDetailInfo(Long cardId);
    List<CardBenefitInfoDTO> getCardBenefits(Long cardId);
}
