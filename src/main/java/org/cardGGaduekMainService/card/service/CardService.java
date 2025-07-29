package org.cardGGaduekMainService.card.service;

import org.cardGGaduekMainService.card.dto.CardFrontDTO;
import org.cardGGaduekMainService.card.dto.CardBackDTO;
import java.util.List;

public interface CardService {
    List<CardFrontDTO> getCardFrontInfo(Long memberId);

    CardBackDTO getCardDetail(Long cardId);

}
