package org.cardGGaduekMainService.main.service;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.exception.CustomException;
import org.cardGGaduekMainService.exception.ErrorCode;
import org.cardGGaduekMainService.main.dto.CardBackDTO;
import org.cardGGaduekMainService.main.dto.CardFrontDTO;
import org.cardGGaduekMainService.main.mapper.MainCardMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainCardServiceImpl implements MainCardService {

    private final MainCardMapper mapper;

    @Override
    public CardFrontDTO getCardFront(Long cardId) {
        CardFrontDTO dto = mapper.getCardFrontById(cardId);
        if (dto == null) {
            throw new CustomException(ErrorCode.CARD_NOT_FOUND);
        }
        return dto;
    }

    @Override
    public CardBackDTO getCardBack(Long cardId) {
        CardBackDTO base = mapper.getCardBackBasicById(cardId);
        if (base == null) {
            throw new CustomException(ErrorCode.CARD_NOT_FOUND);
        }
        List<CardBackDTO.BenefitInfo> benefits = mapper.getCardBenefitsWithCategoryById(cardId);
        base.setBenefits(benefits);
        return base;
    }

    @Override
    public List<CardFrontDTO> getCardList(Long memberId) {
        List<CardFrontDTO> list = mapper.getCardListByMemberId(memberId);
        if (list.isEmpty()) {
            throw new CustomException(ErrorCode.CARD_NOT_FOUND);
        }
        return list;
    }
}
