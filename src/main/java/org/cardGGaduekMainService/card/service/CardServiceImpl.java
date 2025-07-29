package org.cardGGaduekMainService.card.service;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.card.dto.CardBackDTO;
import org.cardGGaduekMainService.card.dto.CardBenefitInfoDTO;
import org.cardGGaduekMainService.card.dto.CardFrontDTO;
import org.cardGGaduekMainService.card.mapper.CardMapper;
import org.cardGGaduekMainService.exception.CustomException;
import org.cardGGaduekMainService.exception.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardMapper cardMapper;

    @Override
    public List<CardFrontDTO> getCardFrontInfo(Long memberId) {
        List<CardFrontDTO> cardList = cardMapper.getCardFrontInfo(memberId);
        if (cardList == null || cardList.isEmpty()) {
            throw new CustomException(ErrorCode.CARD_NOT_FOUND);
        }
        return cardList;
    }

    @Override
    public CardBackDTO getCardDetail(Long cardId) {
        CardBackDTO cardDetail = cardMapper.getCardDetailInfo(cardId);
        if (cardDetail == null) {
            throw new CustomException(ErrorCode.CARD_NOT_FOUND);
        }

        List<CardBenefitInfoDTO> benefits = cardMapper.getCardBenefits(cardId);
        cardDetail.setBenefits(benefits);

        return cardDetail;
    }

    @Override
    public void deleteCard(Long cardId) {
        cardMapper.softDeleteCard(cardId);
    }

    @Override
    public void updateCardImage(Long cardId, String imageUrl) {
        cardMapper.updateCustomImageUrl(cardId, imageUrl);
    }


}
