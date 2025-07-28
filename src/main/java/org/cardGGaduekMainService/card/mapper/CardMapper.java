package org.cardGGaduekMainService.card.mapper;

import org.cardGGaduekMainService.card.dto.CardFrontDTO;
import java.util.List;

public interface CardMapper {
    List<CardFrontDTO> getCardFrontInfo(Long memberId);
}
