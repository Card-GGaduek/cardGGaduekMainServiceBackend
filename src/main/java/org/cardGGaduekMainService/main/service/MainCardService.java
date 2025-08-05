package org.cardGGaduekMainService.main.service;

import org.cardGGaduekMainService.main.dto.CardBackDTO;
import org.cardGGaduekMainService.main.dto.CardFrontDTO;

import java.util.List;

public interface MainCardService {
    CardFrontDTO getCardFront(Long cardId);
    CardBackDTO getCardBack(Long cardId);
    List<CardFrontDTO> getCardList(Long memberId);
}
