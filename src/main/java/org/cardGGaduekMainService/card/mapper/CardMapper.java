package org.cardGGaduekMainService.card.mapper;

import org.cardGGaduekMainService.card.dto.CardResponseDTO;
import java.util.List;

public interface CardMapper {
    List<CardResponseDTO> getCardsByMemberId(Long memberId);
}
