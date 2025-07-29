package org.cardGGaduekMainService.card.service;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.card.mapper.CardMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardMapper cardMapper;

    @Override
    public void deleteCard(Long cardId) {
        cardMapper.softDeleteCard(cardId);
    }
}
