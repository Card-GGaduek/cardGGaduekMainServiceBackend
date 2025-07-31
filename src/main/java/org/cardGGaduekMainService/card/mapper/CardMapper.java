package org.cardGGaduekMainService.card.mapper;

import org.cardGGaduekMainService.card.dto.CardFrontDTO;
import org.cardGGaduekMainService.card.dto.CardBackDTO;
import org.cardGGaduekMainService.card.dto.CardBenefitInfoDTO;
import org.springframework.data.repository.query.Param;
import org.apache.ibatis.annotations.Mapper;
import org.cardGGaduekMainService.card.benefit.domain.CardBenefitVO;
import org.cardGGaduekMainService.card.benefit.domain.CardBenefitVO;
import org.cardGGaduekMainService.card.benefit.domain.CardBenefitVO;
import org.cardGGaduekMainService.card.benefit.domain.CardBenefitVO;

import java.util.List;

@Mapper
public interface CardMapper {
    List<CardFrontDTO> getCardFrontInfo(Long memberId);

    CardBackDTO getCardDetailInfo(Long cardId);
    List<CardBenefitInfoDTO> getCardBenefits(Long cardId);

    int softDeleteCard(@Param("cardId") Long cardId);   // is_valid=0으로 업데이트
    void updateCustomImageUrl(@Param("cardId") Long cardId,
                              @Param("imageUrl") String imageUrl);
    Long findCardProductIdByCardId(Long userCardId);

    List<Long> findAllCardProductIdsByMemberId(Long memberId);
}
