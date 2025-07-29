package org.cardGGaduekMainService.card.mapper;

import org.cardGGaduekMainService.card.dto.CardFrontDTO;
import org.cardGGaduekMainService.card.dto.CardBackDTO;
import org.cardGGaduekMainService.card.dto.CardBenefitInfoDTO;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardMapper {
    List<CardFrontDTO> getCardFrontInfo(Long memberId);

    CardBackDTO getCardDetailInfo(Long cardId);
    List<CardBenefitInfoDTO> getCardBenefits(Long cardId);

    int softDeleteCard(@Param("cardId") Long cardId);   // is_valid=0으로 업데이트
    void updateCustomImageUrl(@Param("cardId") Long cardId,
                              @Param("imageUrl") String imageUrl);
}
