package org.cardGGaduekMainService.card.service;

public interface CardService {
    void deleteCard(Long cardId);
    void updateCardImage(Long cardId, String imageUrl);
}
