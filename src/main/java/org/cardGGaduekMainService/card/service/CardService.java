package org.cardGGaduekMainService.card.service;

import org.cardGGaduekMainService.card.dto.CardFrontDTO;
import org.cardGGaduekMainService.card.dto.CardBackDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CardService {
    List<CardFrontDTO> getCardFrontInfo(Long memberId);
    CardBackDTO getCardDetail(Long cardId);
    void deleteCard(Long cardId);
    void updateCardImage(Long cardId, String imageUrl);

}
