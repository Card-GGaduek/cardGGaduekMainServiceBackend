package org.cardGGaduekMainService.main.service;

import org.cardGGaduekMainService.main.dto.CardBackDTO;
import org.cardGGaduekMainService.main.dto.CardFrontDTO;

public interface MainCardService {
    CardFrontDTO getCardFront(Long cardId);
    CardBackDTO getCardBack(Long cardId);
}
