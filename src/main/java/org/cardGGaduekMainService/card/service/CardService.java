package org.cardGGaduekMainService.card.service;

import org.cardGGaduekMainService.card.dto.CardFrontDTO;
import java.util.List;

public interface CardService {
    List<CardFrontDTO> getCardFrontInfo(Long memberId);
}
