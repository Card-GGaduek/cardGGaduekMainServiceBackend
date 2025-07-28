package org.cardGGaduekMainService.card.service;

import org.cardGGaduekMainService.card.dto.CardResponseDTO;
import java.util.List;

public interface CardService {
    List<CardResponseDTO> getCardsByMemberId(Long memberId);
}
