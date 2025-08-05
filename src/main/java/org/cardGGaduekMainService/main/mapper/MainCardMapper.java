package org.cardGGaduekMainService.main.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.cardGGaduekMainService.main.dto.CardFrontDTO;
import org.cardGGaduekMainService.main.dto.CardBackDTO;

import java.util.List;

@Mapper
public interface MainCardMapper {
    CardFrontDTO getCardFrontById(Long cardId);
    CardBackDTO getCardBackBasicById(Long cardId);
    List<CardBackDTO.BenefitInfo> getCardBenefitsWithCategoryById(Long cardId);
    List<CardFrontDTO> getCardListByMemberId(Long memberId);
}
